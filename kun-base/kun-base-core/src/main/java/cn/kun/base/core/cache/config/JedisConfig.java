package cn.kun.base.core.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis-配置类（兼容老数据）
 * 
 * @author SkySailStar
 * @date 2023-06-13 18:41
 */
@Configuration
public class JedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    
    @Value("${spring.redis.port}")
    private int port;
    
    @Value("${spring.redis.password}")
    private String password;
    
    @Bean
    public JedisPool jedisPool() {
        
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(false);
        return new JedisPool(config, host, port, 0, password);
    }
    
}