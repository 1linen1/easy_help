package com.ateh.eh.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ateh.eh.auth.LoginUser;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.utils.JwtHelper;
import com.ateh.eh.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取Header中的token
        String token = request.getHeader("Authorization");
        if ("/api/user/login".equals(request.getRequestURI())) {
            // 直接放行，让SpringSecurity的其他过滤器报错误
            filterChain.doFilter(request, response);
            return;
        }

        // 解析Token
        Long userId = null;
        try {
            userId = JwtHelper.getUserId(token);
        } catch (Exception e) {
            ResponseUtil.out(response, "Token不合法", 403);
        }

        // 获取Redis中的用户信息
        String value = redisTemplate.opsForValue().get(RedisConstants.LOGIN_KEY + userId);
        LoginUser loginUser = JSON.parseObject(value, LoginUser.class);

        if (Objects.isNull(loginUser)) {
            ResponseUtil.out(response, "登陆已失效！请重新登录！", 403);
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

        // 存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request, response);
    }
}
