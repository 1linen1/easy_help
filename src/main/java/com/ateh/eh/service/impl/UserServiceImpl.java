package com.ateh.eh.service.impl;

import com.alibaba.fastjson.JSON;
import com.ateh.eh.auth.LoginUser;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.User;
import com.ateh.eh.mapper.UserMapper;
import com.ateh.eh.req.user.UserLoginReq;
import com.ateh.eh.service.IUserService;
import com.ateh.eh.utils.JwtHelper;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
                JSON.toJSONString(loginUser), 30 * 60 * 1000, TimeUnit.SECONDS);

        // 组装后返回给前端
        map.put("token", token);

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
}