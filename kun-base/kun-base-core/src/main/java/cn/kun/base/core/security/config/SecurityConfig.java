package cn.kun.base.core.security.config;

import cn.kun.base.core.security.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置
 *
 * @author SkySailStar
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private AuthFilter authFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /*
        关闭csrf（为防止跨站请求伪造，请求携带csrf_token进行校验）。
        对于前后端分离的项目，已经携带了token，能达到和csrf_token一样的效果，不用担心csrf攻击。
        如果这里不禁用，会去校验csrf_token，由于登录请求没有携带，会导致校验失败。
         */
        http.csrf().disable();
        // 不通过Session获取SecurityContext（为了前后端分离）
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 定义请求验证
        http.authorizeRequests()
                // 登录接口放行，允许匿名访问
                .antMatchers("/auth/login").anonymous()
                // WebSocket接口放行，允许匿名访问
                .antMatchers("/websocket/**").anonymous()
                // Swagger3 相关接口允许匿名访问
                .antMatchers(
                        "/",
                        "/swagger-ui.html",
                        "/swagger-ui/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v3/api-docs/**").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        // 将 认证过滤器 放到 用户名密码认证过滤器 之前
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        // 认证授权异常统一处理
        http.exceptionHandling()
                // 认证异常
                .authenticationEntryPoint(authenticationEntryPoint)
                // 授权异常
                .accessDeniedHandler(accessDeniedHandler);
        // 允许跨域
        http.cors();
        // 构建http请求
        return http.build();
    }
}