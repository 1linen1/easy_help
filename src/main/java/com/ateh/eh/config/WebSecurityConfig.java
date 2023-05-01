package com.ateh.eh.config;

import com.ateh.eh.filter.JwtAuthenticationTokenFilter;
import com.ateh.eh.handler.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

//    @Autowired
//    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 等价于 configure(WebSecurity)：配置一些路径放行规则。
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                "/favicon.ico",
                "/static/ptai5mgut1eowaw1.png",
                "/images/**",
                "/static/**",
                "/js/**",
                "/webjars/**",
                "/swagger-ui.html",
                "/swagger/**",
                "/swagger-resources/**",
                "/v2/**");
    }

    /**
     * 等价于 configure(HttpSecurity)：配置 Spring Security 中的过滤器链
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers(
                        "/api/user/login",
                        "/api/user/getVerificationCode",
                        "/api/user/register",
                        "/api/file/uploadTest",
                        "/favicon.ico",
                        "/static/**",
                        "/static/ptai5mgut1eowaw1.png"
                ).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 将认证过滤器添加到这个前面
        http.addFilterBefore(new JwtAuthenticationTokenFilter(redisTemplate), UsernamePasswordAuthenticationFilter.class);
        // 添加自定义处理登录异常与权限校验异常类
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        // 允许跨域
        http.cors();
        return http.build();
    }

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }

}