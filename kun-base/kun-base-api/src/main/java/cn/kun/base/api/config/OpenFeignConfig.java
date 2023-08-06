package cn.kun.base.api.config;

import cn.hutool.core.util.ObjUtil;
import cn.kun.base.api.service.auth.RemoteAuthService;
import cn.kun.base.core.global.constant.BaseConstants;
import cn.kun.base.core.global.util.obj.ObjHelp;
import cn.kun.base.core.global.util.str.StrHelp;
import cn.kun.base.core.security.constant.LoginConstants;
import cn.kun.base.core.security.entity.dto.LoginDTO;
import cn.kun.base.core.security.entity.vo.LoginVO;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 远程服务调用配置类
 *
 * @author SkySailStar
 * @date 2023-01-07 17:45
 */
@Slf4j
@Configuration
public class OpenFeignConfig {
    
    @Value("${spring.profiles.active}")
    private String active;
    
    @Resource
    private RemoteAuthService remoteAuthService;
    
    @Bean
    public RequestInterceptor requestInterceptor() {
        
        return requestTemplate -> {

            String token;
            // 请求方
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (ObjUtil.isNull(requestAttributes)) {
                return;
            } else {
                HttpServletRequest request = requestAttributes.getRequest();
                token = request.getHeader(LoginConstants.AUTHENTICATION);
                // 如果token为空、环境为开发环境，则说明是在单元测试OpenFeign接口，则生成超级管理员的token便于测试使用
                if (StrHelp.isBlank(token) && StrHelp.equals(active, BaseConstants.DEV)) {
                    token = buildTokenByAuth();
                }
            }
            // 被请求方
            requestTemplate.header(LoginConstants.AUTHENTICATION, token);
        };
    }
    
    /**
     * 通过认证服务构建token
     * @return token
     */
    private String buildTokenByAuth() {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLoginName(BaseConstants.ADMIN_LOGIN_NAME);
        loginDTO.setPassword(BaseConstants.ADMIN_PASS_WORD);
        LoginVO loginVO = remoteAuthService.login(loginDTO).getData();
        if (ObjHelp.isNotNull(loginVO)) {
            return loginVO.getToken();
        }
        return null;
    }
}