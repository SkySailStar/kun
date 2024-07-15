package cn.kun.base.core.mq.service;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;

import java.util.Properties;

/**
 * 消息推送-服务层
 *
 * @author 天航星
 * @date 2023-03-08 10:56
 */
public interface RabbitService {

    /**
     * 发送消息
     * @param routingKey 路由标识
     * @param msg 消息
     */
    void send(String routingKey, Object msg);
    
    /**
     * 发送消息
     * @param exchange 交换机
     * @param routingKey 路由标识
     * @param msg 消息
     */
    void send(String exchange, String routingKey, Object msg);

    /**
     * 直连绑定
     * @param queueName 队列名称
     */
    void bindDirect(String queueName);

    /**
     * 直连绑定
     * @param queueName 队列名称
     * @param routingKey 路由标识
     */
    void bindDirect(String queueName, String routingKey);

    /**
     * 直连绑定
     * @param queue 队列
     */
    void bindDirect(Queue queue);

    /**
     * 直连绑定
     * @param queue 队列
     * @param routingKey 路由标识
     */
    void bindDirect(Queue queue, String routingKey);

    /**
     * 扇出绑定
     * @param queueName 队列名称
     */
    void bindFanout(String queueName);

    /**
     * 扇出绑定
     * @param queue 队列
     */
    void bindFanout(Queue queue);

    /**
     * 主题绑定
     * @param queueName 队列名称
     * @param routingKey 路由标识
     */
    void bindTopic(String queueName, String routingKey);

    /**
     * 主题绑定
     * @param queue 队列
     * @param routingKey 路由标识
     */
    void bindTopic(Queue queue, String routingKey);

    /**
     * 头部绑定
     * @param queueName 队列名称
     * @param key 头部-键
     * @param value 头部-值
     */
    void bindHeaders(String queueName, String key, String value);

    /**
     * 头部绑定
     * @param queue 队列
     * @param key 头部-键
     * @param value 头部-值
     */
    void bindHeaders(Queue queue, String key, String value);

    /**
     * 死信绑定
     * @param queueName 队列名称
     */
    void bindDeadLetter(String queueName);

    /**
     * 死信绑定
     * @param queue 队列
     */
    void bindDeadLetter(Queue queue);

    /**
     * 获取队列属性
     * @param queueName 队列名称
     * @return 队列属性
     */
    Properties getQueueProperties(String queueName);

    /**
     * 获取队列信息
     * @param queueName 队列名称
     * @return 队列名称
     */
    QueueInformation getQueueInfo(String queueName);
    
    /**
     * 清空队列
     * @param queueName 队列名称
     * @param noWait 是否不等待清除完成
     */
    void clearQueue(String queueName, boolean noWait);

    /**
     * 清空队列
     * @param queueName 队列名称
     * @return 清空消息的数量
     */
    int clearQueue(String queueName);

    /**
     * 删除队列
     * @param queueName 队列名称
     * @return 结果
     */
    boolean removeQueue(String queueName);

    /**
     * 删除队列
     * @param queueName 队列名称
     * @param unused 是否队列未使用才删除
     * @param empty 是否队列为空才删除
     */
    void removeQueue(String queueName, boolean unused, boolean empty);

    /**
     * 删除交换机
     * @param exchangeName 交换机名称
     * @return 结果
     */
    boolean removeExchange(String exchangeName);
    
    /**
     * 删除绑定关系
     * @param binding 绑定关系
     */
    void removeBinding(Binding binding);
}
