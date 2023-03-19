package com.ateh.eh.handler;

import com.alibaba.fastjson.JSON;
import com.ateh.eh.utils.ResponseUtil;
import com.ateh.eh.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理登录认证失败异常
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        //给前端ResponseResult 的json
        ResponseUtil.out(response, authException.getMessage(), 403);
    }
}