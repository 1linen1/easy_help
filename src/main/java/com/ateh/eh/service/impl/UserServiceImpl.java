package com.ateh.eh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ateh.eh.auth.LoginUser;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.Message;
import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.UserHelpPost;
import com.ateh.eh.entity.ext.UserExt;
import com.ateh.eh.mapper.UserHelpPostMapper;
import com.ateh.eh.req.posts.UpdateUserScoresReq;
import com.ateh.eh.req.user.MyRankReq;
import com.ateh.eh.req.user.RankPageReq;
import com.ateh.eh.req.user.UpdateNicknameReq;
import com.ateh.eh.req.user.UserPageReq;
import com.ateh.eh.utils.EmailTask;
import com.ateh.eh.entity.User;
import com.ateh.eh.mapper.UserMapper;
import com.ateh.eh.req.user.UserLoginReq;
import com.ateh.eh.req.user.UserRegisterReq;
import com.ateh.eh.req.user.VerificationCodeReq;
import com.ateh.eh.service.IUserService;
import com.ateh.eh.utils.JwtHelper;
import com.ateh.eh.utils.Result;
import com.ateh.eh.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Scope(name = "prototype", description = "")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private EmailTask emailTask;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserHelpPostMapper helpPostMapper;

    @Override
    public Result login(UserLoginReq req) {

        HashMap<String, Object> map = new HashMap<>();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());

        // 使用ProviderManager 的auth方法进行验证
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误！");
        }

        // 我们直接实现的 UserDetailServiceImpl 类的返回值 LoginUser类 就是在存储在这里
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        UserExt realUser = loginUser.getUser();

        // 生成token
        String token = JwtHelper.createToken(realUser.getUserId(), realUser.getUsername());

        // 存入Redis中
        redisTemplate.opsForValue().set(RedisConstants.LOGIN_KEY + realUser.getUserId(),
                JSON.toJSONString(loginUser), RedisConstants.TOKEN_VALID_TIME, TimeUnit.SECONDS);

        // 组装后返回给前端
        map.put("token", token);
        map.put("user", realUser);

        realUser.setLastLoginDate(new Date());
        userMapper.updateById(realUser);

        return Result.success(map, "登录成功！");
    }

    @Override
    public Result logout() {
        // 从上下文中获取用户信息
        SecurityContext context = SecurityContextHolder.getContext();
        LoginUser loginUser = UserHolder.getLoginUser();
        redisTemplate.delete(RedisConstants.LOGIN_KEY + loginUser.getUser().getUserId());
        return Result.success("退出成功！");
    }

    @Override
    public Result getVerificationCode(VerificationCodeReq loginCodeReq) {
        String toEmail = loginCodeReq.getEmail();
        if (!Validator.isEmail(toEmail)) {
            return Result.error("邮箱格式错误!");
        }

        // 异步发送
        emailTask.sendAsync(toEmail);

        return Result.success("验证码发送成功!");
    }

    @Override
    public Result register(UserRegisterReq req) {
        String email = req.getEmail();
        if (!Validator.isEmail(email)) {
            return Result.error("邮箱格式错误!");
        }
        if (!StrUtil.equals(req.getCode(), redisTemplate.opsForValue().get(RedisConstants.EMAIL_VERIFICATION_CODE + email))) {
            return Result.error("验证码错误!");
        }
        if (!Objects.isNull(userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email)))) {
            return Result.error("邮箱已被注册!");
        }
        User user = new User();
        user.setEmail(email);
        user.setRole(CommonConstants.NORMAL_USER_2);
        user.setUsername(email);

        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setNickname(RandomUtil.randomString(6));

        user.setAvatar(getAvatar());
        userMapper.insert(user);
        return Result.success("注册成功!");
    }

    private String getAvatar() {
        String[] strings = {
                "https://yeasy-helpj.oss-cn-hangzhou.aliyuncs.com/posts/20230510205806080.jpeg", // 阿贝多
                "https://yeasy-helpj.oss-cn-hangzhou.aliyuncs.com/posts/20230510210324073.jpeg", // 艾诺尔
                "https://yeasy-helpj.oss-cn-hangzhou.aliyuncs.com/posts/20230510210346336.jpeg", // 哒哒哒
                "https://yeasy-helpj.oss-cn-hangzhou.aliyuncs.com/posts/20230510210406115.jpeg", // 将军
                "https://yeasy-helpj.oss-cn-hangzhou.aliyuncs.com/posts/20230510210425483.jpeg", // 可莉
                "https://yeasy-helpj.oss-cn-hangzhou.aliyuncs.com/posts/20230510210451200.jpeg", // 绫华
                "https://yeasy-helpj.oss-cn-hangzhou.aliyuncs.com/posts/20230510210509056.jpeg", // 派蒙
                "https://yeasy-helpj.oss-cn-hangzhou.aliyuncs.com/posts/20230510210533395.jpeg", // 万叶
                "https://yeasy-helpj.oss-cn-hangzhou.aliyuncs.com/posts/20230510210551972.jpeg", // 香菱
                "https://yeasy-helpj.oss-cn-hangzhou.aliyuncs.com/posts/20230510210611745.jpeg", // 心海
        };
        int index = RandomUtil.randomInt(0, strings.length + 1);

        return strings[index];
    }

    @Override
    public Result updateNickname(UpdateNicknameReq req) {
        User user = userMapper.selectById(req.getUserId());
        user.setNickname(req.getNickname());
        userMapper.updateById(user);
        return Result.success("昵称修改成功!");
    }

    @Override
    public Result getRankPage(RankPageReq req) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        if ("Current".equals(req.getOrderType())) {
            lqw.orderBy(true, false, User::getScoresCurrent);
        } else if ("Total".equals(req.getOrderType())) {
            lqw.orderBy(true, false, User::getScoresTotal);
        } else {
            return Result.error("分类类型未指定!");
        }
        lqw.orderBy(true, false, User::getCreateDate);
        lqw.eq(User::getStatus, CommonConstants.STATUS_VALID);
        Page<User> page = new Page<>(req.getPageNum(), req.getPageSize());
        Page<User> userPage = userMapper.selectPage(page, lqw);
        return Result.success(userPage, "查询成功!");
    }

    @Override
    public Result<UserExt> getMyRank(MyRankReq req) {
        String orderType = req.getOrderType();

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw2 = new LambdaQueryWrapper<>();
        User tempUser = userMapper.selectById(req.getUserId());
        UserExt userExt = userMapper.qryUserByName(tempUser.getUsername());

        if ("Current".equals(orderType)) {
            lqw.gt(true, User::getScoresCurrent, userExt.getScoresCurrent());
            lqw2.eq(true, User::getScoresCurrent, userExt.getScoresCurrent());
        } else if ("Total".equals(orderType)) {
            lqw.gt(true, User::getScoresTotal, userExt.getScoresTotal());
            lqw2.eq(true, User::getScoresTotal, userExt.getScoresTotal());
        } else {
            return Result.error("分类类型未指定!");
        }
        lqw.eq(User::getStatus, CommonConstants.STATUS_VALID);

        lqw2.gt(User::getCreateDate, userExt.getCreateDate());
        lqw2.eq(User::getStatus, CommonConstants.STATUS_VALID);

        Long count = userMapper.selectCount(lqw);
        Long count2 = userMapper.selectCount(lqw2);

        userExt.setRank(++count + count2);
        return Result.success(userExt, "查询成功!");
    }

    @Override
    public Result getUserInfo(Long userId) {
        return Result.success(userMapper.getUserInfo(userId));
    }

    @Override
    public Result qryChatList(Long userId) {
        List<UserExt> userList = userMapper.qryChatList(userId);

        List<UserExt> list = userList.stream().peek(item -> {
            String value = redisTemplate.opsForValue().get(RedisConstants.LAST_MESSAGE + userId + ":" + item.getUserId());
            if (StrUtil.isNotEmpty(value)) {
                item.setLastMessage(JSON.parseObject(value, Message.class));
            }
        }).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result qryHelpUserList(Post req) {
        return Result.success(userMapper.qryHelpUserList(req));
    }

    @Override
    public Result updateUserScores(UpdateUserScoresReq req) {
        List<UserExt> userExtList = req.getUserExtList();
        userExtList.forEach(userExt -> {
            User user = new User();
            user.setUserId(userExt.getUserId());
            user.setScoresCurrent(userExt.getScoresCurrent() + userExt.getAssignedScores());
            user.setScoresTotal(userExt.getScoresTotal() + userExt.getAssignedScores());

            String value = redisTemplate.opsForValue().get(RedisConstants.POST_HELP + userExt.getUserId() + ":" + req.getPostId());

            LambdaQueryWrapper<UserHelpPost> lqw = new LambdaQueryWrapper<>();
            lqw.eq(UserHelpPost::getPostId, req.getPostId());
            lqw.eq(UserHelpPost::getPassiveUserId, req.getUserId());
            lqw.eq(UserHelpPost::getPositiveUserId, userExt.getUserId());
            lqw.ne(UserHelpPost::getStatus, CommonConstants.STATUS_INVALID);

            UserHelpPost userHelpPost = new UserHelpPost();
            userHelpPost.setPostId(req.getPostId());
            userHelpPost.setPassiveUserId(req.getUserId());
            userHelpPost.setPositiveUserId(userExt.getUserId());
            userHelpPost.setScores(userExt.getAssignedScores());
            userHelpPost.setStatus(CommonConstants.POST_RESOLVED);
            if (StrUtil.isEmpty(value)) {
                // 如果为空，二次检查是否已经有数据
                long count = helpPostMapper.selectCount(lqw);
                if (count <= 0) {
                    // 没有记录，说明是私聊的，不是评论帮助，插入数据
                    helpPostMapper.insert(userHelpPost);
                } else {
                    // 说明已经有记录了，更新状态
                    helpPostMapper.update(userHelpPost, lqw);
                }
            } else {
                // 说明肯定有记录，直接更新状态
                helpPostMapper.update(userHelpPost, lqw);
            }
            redisTemplate.delete(RedisConstants.POST_HELP + userExt.getUserId() + ":" + req.getPostId());
            userMapper.updateById(user);
        });
        return Result.success("分配积分成功!");
    }

    @Override
    public Result getAllUserPage(UserPageReq req) {
        IPage<UserExt> userList = userMapper.getAllUserPage(req.toPage(), req);
        return Result.success(userList);
    }

    @Override
    public Result updateUser(User user) {
        userMapper.updateById(user);
        if (CommonConstants.STATUS_VALID.equals(user.getStatus())) {
            return Result.success("恢复成功!");
        } else {
            return Result.success("删除成功!");
        }
    }
}