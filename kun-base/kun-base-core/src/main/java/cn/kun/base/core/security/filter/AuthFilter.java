package cn.kun.base.core.security.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.kun.base.core.cache.constant.AuthCacheConstants;
import cn.kun.base.core.cache.util.RedisUtils;
import cn.kun.base.core.security.constant.LoginConstants;
import cn.kun.base.core.security.entity.LoginUser;
import cn.kun.base.core.security.util.JwtUtils;
import cn.kun.base.core.security.util.WebUtils;
import com.alibaba.fastjson2.JSON;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.HttpStatusConstants;
import cn.kun.base.core.global.entity.BaseResult;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 认证过滤器
 *
 * @author 天航星
 */
@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        // 获取token
        String token = request.getHeader(LoginConstants.AUTHENTICATION);
        // 如果没有携带token或者是登录接口，则放行，交由后面的过滤器验证
        if (StrUtil.isBlank(token) || StrUtil.equals(request.getRequestURI(), LoginConstants.LOGIN_PATH)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 如果包含前缀，去掉前缀
        if (token.startsWith(LoginConstants.BEARER)) {
            token = token.replace(LoginConstants.BEARER, "");
        }
        // 解析token
        Claims claims = JwtUtils.parse(token);
        // 获取用户ID
        String userId = claims.getSubject();
        // 从Redis中获取用户信息
        Object cacheObj = RedisUtils.getHash(AuthCacheConstants.LOGIN_INFO_HASH, userId);
        if (Objects.isNull(cacheObj)) {
            log.warn("请求地址：{}, 用户未登录", request.getRequestURI());
            // 封装异常消息到响应体
            WebUtils.buildResponse(response, Convert.toInt(HttpStatusConstants.UNAUTHORIZED), JSON.toJSONString(BaseResult.fail(ErrorCodeConstants.NO_LOGIN, "用户未登录")));
            return;
        }
        // 获取权限信息
        if (cacheObj instanceof LoginUser loginUser) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            // 封装到Authentication中，方便后面获取
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 放行
        filterChain.doFilter(request, response);
    }

}