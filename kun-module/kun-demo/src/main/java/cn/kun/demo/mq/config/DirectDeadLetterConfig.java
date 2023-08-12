package cn.kun.demo.mq.config;

import cn.kun.demo.mq.constants.DemoQueueConstants;
import cn.kun.base.core.mq.constant.ExchangeConstants;
import cn.kun.base.core.mq.service.RabbitService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 死信队列-配置类
 *
 * @author SkySailStar
 * @date 2023-04-04 09:49
 */
@Configuration
public class DirectDeadLetterConfig {

    @Autowired
    private RabbitService rabbitService;
    
    @Bean
    public Object directDeadLetterInit() {

        // 普通队列（示例）
        Queue queue = QueueBuilder
                // 持久化类型
                .durable(DemoQueueConstants.DIRECT_DEMO_NORMAL)
                // 指定死信交换机
                .deadLetterExchange(ExchangeConstants.DEAD_LETTER)
                // 指定死信路由标识
                .deadLetterRoutingKey(DemoQueueConstants.DIRECT_DEMO_NORMAL_DEAD_LETTER)
                // 如果10秒没处理，就自动删除
                .ttl(10 * 1000)
                // 最大长度设定为3
                .maxLength(3)
                .build();
        rabbitService.bindDirect(queue);

        // 死信队列（示例）
        rabbitService.bindDeadLetter(DemoQueueConstants.DIRECT_DEMO_NORMAL_DEAD_LETTER);
        
        return new Object();
    }
    
}
