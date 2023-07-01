package cn.kun.demo.mq.config;

import cn.kun.demo.mq.constants.DemoQueueConstants;
import cn.kun.demo.mq.constants.DemoRoutingConstants;
import cn.kun.base.core.mq.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主题模式-配置类
 *
 * @author 廖航
 * @date 2023-04-19 16:11
 */
@Configuration
public class TopicConfig {

    @Autowired
    private RabbitService rabbitService;
    
    @Bean
    public Object topicInit() {

        // 主题模式队列（示例）
        rabbitService.bindTopic(DemoQueueConstants.TOPIC_DEMO_BUSINESS, DemoRoutingConstants.TOPIC_DEMO);

        return new Object();
    }
    
}
