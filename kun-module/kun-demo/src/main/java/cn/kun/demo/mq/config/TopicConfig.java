package cn.kun.demo.mq.config;

import cn.kun.demo.mq.constants.DemoQueueConstants;
import cn.kun.demo.mq.constants.DemoRoutingConstants;
import cn.kun.base.core.mq.service.RabbitService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主题模式-配置类
 *
 * @author SkySailStar
 * @date 2023-04-19 16:11
 */
@Configuration
public class TopicConfig {

    @Resource
    private RabbitService rabbitService;
    
    @Bean
    public Object topicInit() {

        // 主题模式队列（示例）
        rabbitService.bindTopic(DemoQueueConstants.TOPIC_DEMO_BUSINESS, DemoRoutingConstants.TOPIC_DEMO);

        return new Object();
    }
    
}
