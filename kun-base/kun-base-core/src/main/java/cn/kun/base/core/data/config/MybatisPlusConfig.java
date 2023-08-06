package cn.kun.base.core.data.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import cn.kun.base.core.data.handler.MetaHandler;
import cn.kun.base.core.global.config.CustomIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus配置类
 *
 * @author SkySailStar
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 单页分页条数限制（默认500）
     */
    private static final Long MAX_LIMIT = 100L;

    /**
     * 配置分页拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor () {
        // 定义拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        /* 设置最大分页查询数 */
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setMaxLimit(MAX_LIMIT);
        
        // 添加分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        // 防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * 自动填充功能
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MetaHandler());
        return globalConfig;
    }

    /**
     * 自定义主键生成
     */
    @Bean
    public IdentifierGenerator identifierGenerator() {
        return new CustomIdGenerator();
    }

    /**
     * 自定义方法注入
     */
    @Bean
    public CustomSqlInjector mySqlInjector() {
        return new CustomSqlInjector();
    }
}
