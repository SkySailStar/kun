package cn.kun.base.core.global.config;

import cn.kun.base.core.global.enums.ServiceEnum;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc API文档相关配置
 *
 * @author SkySailStar
 * @date 2023-02-02 16:50
 */
@Configuration
public class SpringDocConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI openApi() {
        
        String title = ServiceEnum.getDescByCode(applicationName);
        return new OpenAPI()
                .info(new Info()
                        // 标题
                        .title(title)
                        // 描述
                        .description("云端组 - " + title + " - 接口文档"));
    }
}