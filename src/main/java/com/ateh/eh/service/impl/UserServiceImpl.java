package com.ateh.eh.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.ateh.eh.auth.LoginUser;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.Email;
import com.ateh.eh.entity.User;
import com.ateh.eh.mapper.EmailMapper;
import com.ateh.eh.mapper.UserMapper;
import com.ateh.eh.req.user.UserLoginReq;
import com.ateh.eh.req.user.VerificationCodeReq;
import com.ateh.eh.service.IUserService;
import com.ateh.eh.utils.JwtHelper;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 获得发件人信息
     */
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

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

        User realUser = loginUser.getUser();

        // 生成token
        String token = JwtHelper.createToken(realUser.getUserId(), realUser.getUsername());

        // 存入Redis中
        redisTemplate.opsForValue().set(RedisConstants.LOGIN_KEY + realUser.getUserId(),
                JSON.toJSONString(loginUser), RedisConstants.TOKEN_VALID_TIME, TimeUnit.SECONDS);

        // 组装后返回给前端
        map.put("token", token);
        map.put("user", realUser);

        return Result.success(map, "登录成功！");
    }

    @Override
    public Result logout() {
        // 从上下文中获取用户信息
        SecurityContext context = SecurityContextHolder.getContext();
        LoginUser loginUser = (LoginUser) context.getAuthentication().getPrincipal();
        redisTemplate.delete(RedisConstants.LOGIN_KEY + loginUser.getUser().getUserId());
        return Result.success("退出成功！");
    }

    @Override
    public Result getVerificationCode(VerificationCodeReq loginCodeReq) {
        String toEmail = loginCodeReq.getEmail();
        if (!Validator.isEmail(toEmail)) {
            return Result.error("邮箱格式错误!");
        }
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(fromEmail);
        smm.setTo(toEmail);
        smm.setSubject("《易帮》邮箱验证码");
        String verCode = RandomUtil.randomNumbers(6);
        smm.setText("尊敬的用户您好:"
                + "\n您的验证码是：" + verCode + "，本验证码 5 分钟内效，请及时输入（请勿泄露此验证码），如非本人操作，请忽略该邮件。");
        mailSender.send(smm);
        redisTemplate.opsForValue().set(RedisConstants.EMAIL_VERIFICATION_CODE + toEmail, verCode, RedisConstants.EMAIL_CODE_VALID_TIME,  TimeUnit.SECONDS);
        emailMapper.insert(new Email(toEmail, fromEmail, smm.getSubject(), smm.getText()));
        return Result.success("验证码发送成功!");
    }
}