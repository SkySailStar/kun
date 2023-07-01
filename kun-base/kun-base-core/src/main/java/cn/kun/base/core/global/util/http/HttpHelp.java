package cn.kun.base.core.global.util.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.net.SocketTimeoutException;

/**
 * Http请求-工具类
 *
 * @author 廖航
 * @date 2023-05-09 16:21
 */
@Slf4j
@Component
public class HttpHelp {

    private static RestTemplate restTemplate;
    
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        
        HttpHelp.restTemplate = restTemplate;
    }
    
    /**
     * 调用算法接口
     *
     * @param url 路径
     * @param object 参数
     * @return str 返回值
     */
    public static String postAi(String url, Object object) {

        //响应消息
        String str = "";
        // 请求接口
        try {
            str = restTemplate.postForEntity(url, object, String.class).getBody();
        } catch (Exception e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                log.info("算法接口调用成功，等待算法处理");
            } else {
                try {
                    str = restTemplate.postForEntity(url, object, String.class).getBody();
                } catch (Exception e1) {
                    if (e1.getCause() instanceof SocketTimeoutException) {
                        log.info("算法接口调用成功，等待算法处理");
                    } else {
                        log.error("算法接口调用失败", e1);
                    }
                }
            }
        }
        return str;
    }
    
}
