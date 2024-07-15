package cn.kun.base.core.security.config;

import cn.kun.base.core.security.filter.AuthFilter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 安全配置
 *
 * @author 天航星
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private AuthFilter authFilter;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
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
        http.csrf(AbstractHttpConfigurer::disable);
        // 允许跨域
        http.cors(withDefaults());
        // 不通过Session获取SecurityContext（为了前后端分离）
        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 定义请求验证
        http.authorizeHttpRequests(config -> config
                // 登录接口放行，允许匿名访问
                .requestMatchers("/auth/login").anonymous()
                // WebSocket接口放行，允许匿名访问
                .requestMatchers("/websocket/**").anonymous()
                // Swagger3 相关接口允许匿名访问
                .requestMatchers(
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
                .anyRequest().authenticated());
        // 将 认证过滤器 放到 用户名密码认证过滤器 之前
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        // 认证授权异常统一处理
        http.exceptionHandling(config -> config
                // 认证异常
                .authenticationEntryPoint(authenticationEntryPoint)
                // 授权异常
                .accessDeniedHandler(accessDeniedHandler)
        );
        // 构建http请求
        return http.build();
    }
}