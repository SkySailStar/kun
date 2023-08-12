package cn.kun.demo.mq.config;

import cn.kun.demo.mq.constants.DemoQueueConstants;
import cn.kun.base.core.mq.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 直连模式-配置类
 *
 * @author SkySailStar
 * @date 2023-04-04 09:49
 */
@Configuration
public class DirectConfig {
    
    @Autowired
    private RabbitService rabbitService;
    
    @Bean
    public Object directInit() {

        // 直连模式队列（示例）
        rabbitService.bindDirect(DemoQueueConstants.DIRECT_DEMO_BUSINESS);

        return new Object();
    }
    
}
