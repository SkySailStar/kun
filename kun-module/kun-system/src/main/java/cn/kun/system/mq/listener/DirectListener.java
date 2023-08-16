package cn.kun.system.mq.listener;

import cn.kun.system.error.service.ErrorInfoService;
import cn.kun.base.core.mq.constant.QueueConstants;
import cn.kun.base.core.mq.entity.dto.ErrorInfoMqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 直连模式-监听器
 *
 * @author SkySailStar
 * @date 2023-04-04 15:35
 */
@Slf4j
@Component
public class DirectListener {
    
    @Resource
    private ErrorInfoService errorInfoService;
    
    @RabbitListener(queuesToDeclare = {@Queue(QueueConstants.DIRECT_SYSTEM_ERROR_INFO)})
    public void consume(ErrorInfoMqDTO dto) {
        
        log.info("直连模式队列：{}，接收到消息：{}", QueueConstants.DIRECT_SYSTEM_ERROR_INFO, dto);
        errorInfoService.add(dto);
    }
}
