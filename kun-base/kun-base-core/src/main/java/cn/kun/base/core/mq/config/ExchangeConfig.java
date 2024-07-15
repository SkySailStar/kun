package cn.kun.base.core.mq.config;

import cn.kun.base.core.mq.constant.ExchangeConstants;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ交换机-配置类
 *
 * @author 天航星
 * @date 2023-04-24 15:20
 */
@Configuration
public class ExchangeConfig {

    /**
     * 直连交换机
     */
    @Bean
    public DirectExchange directExchange() {

        return new DirectExchange(ExchangeConstants.DIRECT);
    }

    /**
     * 主题交换机
     */
    @Bean
    public TopicExchange topicExchange() {

        return new TopicExchange(ExchangeConstants.TOPIC);
    }

    /**
     * 扇出交换机
     */
    @Bean
    public FanoutExchange fanoutExchange() {

        return new FanoutExchange(ExchangeConstants.FANOUT);
    }

    /**
     * 头部交换机
     */
    @Bean
    public HeadersExchange headersExchange() {

        return new HeadersExchange(ExchangeConstants.HEADERS);
    }

    /**
     * 直连-死信交换机
     */
    @Bean
    public DirectExchange directDeadLetterExchange() {

        return new DirectExchange(ExchangeConstants.DEAD_LETTER);
    }
    
}
