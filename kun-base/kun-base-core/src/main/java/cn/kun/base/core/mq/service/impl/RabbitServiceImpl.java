package cn.kun.base.core.mq.service.impl;

import cn.kun.base.core.mq.service.RabbitService;
import cn.kun.base.core.mq.constant.ExchangeConstants;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * 消息推送-服务层实现类
 *
 * @author 天航星
 */
@Service
public class RabbitServiceImpl implements RabbitService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private AmqpAdmin amqpAdmin;

    @Resource
    private DirectExchange directExchange;

    @Resource
    private FanoutExchange fanoutExchange;

    @Resource
    private TopicExchange topicExchange;

    @Resource
    private HeadersExchange headersExchange;

    @Resource
    private DirectExchange directDeadLetterExchange;

    @Override
    public void send(String routingKey, Object msg) {
        send(ExchangeConstants.DIRECT, routingKey, msg);
    }

    @Override
    public void send(String exchange, String routingKey, Object msg) {
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
    }

    @Override
    public void bindDirect(String queueName) {

        // 配置队列
        Queue queue = initQueue(queueName);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(directExchange)
                        // 队列名称作为路由标识
                        .withQueueName()
        );
    }

    @Override
    public void bindDirect(String queueName, String routingKey) {

        // 配置队列
        Queue queue = initQueue(queueName);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(directExchange)
                        // 路由标识
                        .with(routingKey)
        );
    }

    @Override
    public void bindDirect(Queue queue) {

        // 配置队列
        amqpAdmin.declareQueue(queue);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(directExchange)
                        // 队列名称作为路由标识
                        .withQueueName()
        );
    }

    @Override
    public void bindDirect(Queue queue, String routingKey) {

        // 配置队列
        amqpAdmin.declareQueue(queue);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(directExchange)
                        // 路由标识
                        .with(routingKey)
        );
    }

    @Override
    public void bindFanout(String queueName) {

        // 配置队列
        Queue queue = initQueue(queueName);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(fanoutExchange)
        );
    }

    @Override
    public void bindFanout(Queue queue) {

        // 配置队列
        amqpAdmin.declareQueue(queue);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(fanoutExchange)
        );
    }

    @Override
    public void bindTopic(String queueName, String routingKey) {

        // 配置队列
        Queue queue = initQueue(queueName);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(topicExchange)
                        // 路由标识
                        .with(routingKey)
        );
    }

    @Override
    public void bindTopic(Queue queue, String routingKey) {

        // 配置队列
        amqpAdmin.declareQueue(queue);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(topicExchange)
                        // 路由标识
                        .with(routingKey)
        );
    }

    @Override
    public void bindHeaders(String queueName, String key, String value) {

        // 配置队列
        Queue queue = initQueue(queueName);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(headersExchange)
                        // 头部-键
                        .where(key)
                        // 头部-值
                        .matches(value)
        );
    }

    @Override
    public void bindHeaders(Queue queue, String key, String value) {

        // 配置队列
        amqpAdmin.declareQueue(queue);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(headersExchange)
                        // 头部-键
                        .where(key)
                        // 头部-值
                        .matches(value)
        );
    }

    @Override
    public void bindDeadLetter(String queueName) {

        // 配置队列
        Queue queue = initQueue(queueName);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(directDeadLetterExchange)
                        // 队列名称作为路由标识
                        .withQueueName()
        );
    }

    @Override
    public void bindDeadLetter(Queue queue) {

        // 配置队列
        amqpAdmin.declareQueue(queue);
        // 配置绑定关系
        amqpAdmin.declareBinding(
                BindingBuilder
                        // 绑定队列
                        .bind(queue)
                        // 到交换机
                        .to(directDeadLetterExchange)
                        // 队列名称作为路由标识
                        .withQueueName()
        );
    }

    @Override
    public Properties getQueueProperties(String queueName) {
        return amqpAdmin.getQueueProperties(queueName);
    }

    @Override
    public QueueInformation getQueueInfo(String queueName) {
        return amqpAdmin.getQueueInfo(queueName);
    }
    
    @Override
    public void clearQueue(String queueName, boolean noWait) {
        amqpAdmin.purgeQueue(queueName, noWait);
    }

    @Override
    public int clearQueue(String queueName) {
        return amqpAdmin.purgeQueue(queueName);
    }
    
    @Override
    public boolean removeQueue(String queueName) {
        return amqpAdmin.deleteQueue(queueName);
    }

    @Override
    public void removeQueue(String queueName, boolean unused, boolean empty) {
        amqpAdmin.deleteQueue(queueName, unused, empty);
    }

    @Override
    public boolean removeExchange(String exchangeName) {
        return amqpAdmin.deleteExchange(exchangeName);
    }
    
    @Override
    public void removeBinding(Binding binding) {
        amqpAdmin.removeBinding(binding);
    }

    /**
     * 配置普通队列
     * @param queueName 队列名称
     */
    private Queue initQueue(String queueName) {

        // 定义队列
        Queue queue = new Queue(queueName);
        // 配置队列
        amqpAdmin.declareQueue(queue);
        return queue;
    }
    
}
