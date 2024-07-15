package cn.kun.demo.mq.config;

import cn.kun.demo.mq.constants.DemoQueueConstants;
import cn.kun.demo.mq.constants.DemoRoutingConstants;
import cn.kun.base.core.mq.service.RabbitService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 头部模式-配置类
 *
 * @author 天航星
 * @date 2023-04-04 09:49
 */
@Configuration
public class HeadersConfig {

    @Resource
    private RabbitService rabbitService;
    
    @Bean
    public Object headersInit() {

        // 头部模式队列（示例）
        rabbitService.bindHeaders(DemoQueueConstants.HEADERS_DEMO_BUSINESS, DemoRoutingConstants.HEADERS_DEMO_KEY, DemoRoutingConstants.HEADERS_DEMO_VALUE);

        return new Object();
    }
    
}
