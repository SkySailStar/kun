package cn.kun.base.core.security.handler;

import cn.hutool.core.convert.Convert;
import cn.kun.base.core.security.util.WebHelp;
import com.alibaba.fastjson2.JSON;
import cn.kun.base.core.global.constant.HttpStatusConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 授权失败处理器
 *
 * @author SkySailStar
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        // 封装异常消息到响应体
        WebHelp.buildResponse(response, Convert.toInt(HttpStatusConstants.UNAUTHORIZED), JSON.toJSONString(BaseResult.fail(HttpStatusConstants.UNAUTHORIZED, "权限不足，请联系管理员授权")));
    }
}