package com.ateh.eh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ateh.eh.auth.LoginUser;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.ext.UserExt;
import com.ateh.eh.req.user.MyRankReq;
import com.ateh.eh.req.user.RankPageReq;
import com.ateh.eh.req.user.UpdateNicknameReq;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
        userMapper.insert(user);
        return Result.success("注册成功!");
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
    public Result getMyRank(MyRankReq req) {
        String orderType = req.getOrderType();

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lqw2 = new LambdaQueryWrapper<>();
        User user = userMapper.selectById(req.getUserId());

        if ("Current".equals(orderType)) {
            lqw.gt(true, User::getScoresCurrent, user.getScoresCurrent());
            lqw2.eq(true, User::getScoresCurrent, user.getScoresCurrent());
        } else if ("Total".equals(orderType)) {
            lqw.gt(true, User::getScoresTotal, user.getScoresTotal());
            lqw2.eq(true, User::getScoresTotal, user.getScoresTotal());
        } else {
            return Result.error("分类类型未指定!");
        }
        lqw.eq(User::getStatus, CommonConstants.STATUS_VALID);

        lqw2.gt(User::getCreateDate, user.getCreateDate());
        lqw2.eq(User::getStatus, CommonConstants.STATUS_VALID);

        Long count = userMapper.selectCount(lqw);
        Long count2 = userMapper.selectCount(lqw2);

        UserExt userExt = BeanUtil.toBean(user, UserExt.class);
        userExt.setRank(++count + count2);
        return Result.success(userExt, "查询成功!");
    }
}