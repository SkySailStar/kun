package cn.kun.demo.mq.listener;

import cn.kun.demo.mq.constants.DemoQueueConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 头部模式-监听器
 *
 * @author 天航星
 * @date 2023-04-04 15:35
 */
@Slf4j
@Component
public class HeadersListener {

    /**
     * 头部模式队列（示例）
     */
    @RabbitListener(queuesToDeclare = {@Queue(DemoQueueConstants.HEADERS_DEMO_BUSINESS)})
    public void consume(String area) {
        
        log.info("头部模式队列：{}", area);
    }
}
