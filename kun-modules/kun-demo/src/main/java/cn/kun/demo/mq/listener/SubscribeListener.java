package cn.kun.demo.mq.listener;

import cn.kun.demo.crud.entity.po.Area;
import cn.kun.demo.mq.constants.DemoQueueConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 发布订阅模式-监听器
 *
 * @author SkySailStar
 * @date 2023-04-19 18:11
 */
@Slf4j
@Component
public class SubscribeListener {

    /**
     * 发布订阅模式队列1（示例）
     */
    @RabbitListener(queuesToDeclare = {@Queue(DemoQueueConstants.SUBSCRIBE_DEMO_BUSINESS1)})
    public void consumeAi(Area area) {
        
        log.info("发布订阅模式队列1：{}", area.getName());
    }

    /**
     * 发布订阅模式队列2（示例）
     */
    @RabbitListener(queuesToDeclare = {@Queue(DemoQueueConstants.SUBSCRIBE_DEMO_BUSINESS2)})
    public void consumeRbp(Area area) {
        
        log.info("发布订阅模式队列2：{}", area.getName());
    }
    
}
