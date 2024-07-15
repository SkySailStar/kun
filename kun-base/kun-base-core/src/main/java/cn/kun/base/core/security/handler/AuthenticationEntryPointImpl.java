package cn.kun.base.core.security.handler;

import cn.hutool.core.convert.Convert;
import cn.kun.base.core.security.util.WebUtils;
import com.alibaba.fastjson2.JSON;
import cn.kun.base.core.global.constant.HttpStatusConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证失败处理器
 *
 * @author 天航星
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        // 封装异常消息到响应体
        WebUtils.buildResponse(response, Convert.toInt(HttpStatusConstants.UNAUTHORIZED), JSON.toJSONString(BaseResult.fail(HttpStatusConstants.UNAUTHORIZED, "认证失败")));
    }
}