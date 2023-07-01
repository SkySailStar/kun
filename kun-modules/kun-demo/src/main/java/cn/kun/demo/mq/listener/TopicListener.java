package cn.kun.demo.mq.listener;

import cn.kun.demo.crud.entity.po.Area;
import cn.kun.demo.mq.constants.DemoQueueConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 主题模式-监听器
 *
 * @author 廖航
 * @date 2023-04-04 15:35
 */
@Slf4j
@Component
public class TopicListener {

    /**
     * 主题模式队列（示例）
     */
    @RabbitListener(queuesToDeclare = {@Queue(DemoQueueConstants.TOPIC_DEMO_BUSINESS)})
    public void consume(Area area) {
        
        log.info("主题模式队列：{}", area.getName());
    }
}
