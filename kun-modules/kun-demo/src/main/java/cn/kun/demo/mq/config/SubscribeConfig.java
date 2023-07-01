package cn.kun.demo.mq.config;

import cn.kun.demo.mq.constants.DemoQueueConstants;
import cn.kun.base.core.mq.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 发布订阅模式-配置类
 *
 * @author 廖航
 * @date 2023-04-19 16:11
 */
@Configuration
public class SubscribeConfig {

    @Autowired
    private RabbitService rabbitService;
    
    @Bean
    public Object fanoutInit() {

        // 发布订阅模式队列1（示例）
        rabbitService.bindFanout(DemoQueueConstants.SUBSCRIBE_DEMO_BUSINESS1);
        // 发布订阅模式队列2（示例）
        rabbitService.bindFanout(DemoQueueConstants.SUBSCRIBE_DEMO_BUSINESS2);
        
        return new Object();
    }
}
