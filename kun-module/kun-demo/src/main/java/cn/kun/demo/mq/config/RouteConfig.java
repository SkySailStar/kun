package cn.kun.demo.mq.config;

import cn.kun.demo.mq.constants.DemoQueueConstants;
import cn.kun.demo.mq.constants.DemoRoutingConstants;
import cn.kun.base.core.mq.service.RabbitService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 路由模式-配置类
 *
 * @author SkySailStar
 * @date 2023-04-04 09:49
 */
@Configuration
public class RouteConfig {

    @Resource
    private RabbitService rabbitService;
    
    @Bean
    public Object routeInit() {

        // 路由模式队列（示例）
        rabbitService.bindDirect(DemoQueueConstants.ROUTE_DEMO_BUSINESS, DemoRoutingConstants.ROUTE_DEMO_BUSINESS1);
        // 路由模式队列（示例）
        rabbitService.bindDirect(DemoQueueConstants.ROUTE_DEMO_BUSINESS, DemoRoutingConstants.ROUTE_DEMO_BUSINESS2);
        
        return new Object();
    }
}
