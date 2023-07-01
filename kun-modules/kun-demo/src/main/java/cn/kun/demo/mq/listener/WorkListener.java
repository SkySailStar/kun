package cn.kun.demo.mq.listener;

import cn.kun.demo.mq.constants.DemoQueueConstants;
import cn.kun.demo.crud.entity.po.Area;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 工作模式-监听器
 *
 * @author 廖航
 * @date 2023-04-04 15:35
 */
@Slf4j
@Component
public class WorkListener {

    /**
     * 工作模式队列（示例）
     */
    @RabbitListener(queuesToDeclare = {@Queue(DemoQueueConstants.WORK_DEMO_BUSINESS)})
    public void consume1(Area area) {
        
        log.info("工作模式队列1：{}", area.getName());
    }

    /**
     * 工作模式队列（示例）
     */
    @RabbitListener(queuesToDeclare = {@Queue(DemoQueueConstants.WORK_DEMO_BUSINESS)})
    public void consume2(Area area) {
        
        log.info("工作模式队列2：{}", area.getName());
    }
}
