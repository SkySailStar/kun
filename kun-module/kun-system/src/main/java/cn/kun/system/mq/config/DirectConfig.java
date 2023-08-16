package cn.kun.system.mq.config;

import cn.kun.base.core.mq.constant.QueueConstants;
import cn.kun.base.core.mq.service.RabbitService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 直连模式-配置类
 *
 * @author SkySailStar
 * @date 2023-04-24 13:57
 */
@Configuration
public class DirectConfig {

    @Resource
    private RabbitService rabbitService;
    
    @Bean
    public Object directInit() {

        // 直连模式-系统服务-错误信息
        rabbitService.bindDirect(QueueConstants.DIRECT_SYSTEM_ERROR_INFO);
        
        return new Object();
    }
    
}
