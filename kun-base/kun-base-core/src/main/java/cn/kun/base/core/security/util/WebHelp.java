package cn.kun.base.core.security.util;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 网页工具类
 * 
 * @author SkySailStar
 */
public class WebHelp {
    
    /**
     * 封装到响应体
     *
     * @param response 响应体
     * @param code 编码
     * @param message 消息
     */
    public static void buildResponse(HttpServletResponse response, int code, String message) {
        try {
            response.setStatus(code);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}