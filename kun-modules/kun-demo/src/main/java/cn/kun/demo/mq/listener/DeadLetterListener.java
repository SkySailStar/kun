package cn.kun.demo.mq.listener;

import cn.hutool.core.util.ObjUtil;
import cn.kun.demo.crud.entity.po.Area;
import cn.kun.demo.mq.constants.DemoQueueConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * 死信队列-监听器
 *
 * @author 廖航
 * @date 2023-04-04 15:35
 */
@Slf4j
@Component
public class DeadLetterListener {

    /**
     * 普通队列（示例）
     */
    @RabbitListener(queues = DemoQueueConstants.DIRECT_DEMO_NORMAL)
    public void consume1(Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Area area) throws IOException {

        // 拒绝消息。第二个参数为true则消息返回队列，第二个参数为false则消息不返回队列，成为死信
        if (ObjUtil.isNull(area.getId())) {
            channel.basicReject(deliveryTag, false);
        }
    }

    /**
     * 死信队列（示例）
     */
    @RabbitListener(queuesToDeclare = {@Queue(DemoQueueConstants.DIRECT_DEMO_NORMAL_DEAD_LETTER)})
    public void consume2(Area area) {
        
        log.info("死信队列：{}", area.getName());
    }
}
