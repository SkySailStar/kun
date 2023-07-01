package cn.kun.base.core.cache.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Redis 配置类
 *
 * @author 廖航
 */
@Configuration
@EnableCaching
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.database}")
    private int database;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置数据库
        connectionFactory.setDatabase(database);
        // 刷新配置
        connectionFactory.afterPropertiesSet();
        // 重置连接
        connectionFactory.resetConnection();
        // 设置连接
        template.setConnectionFactory(connectionFactory);
        // 使用Jackson2JsonRedisSerialize替换默认序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域：ALL代表所有；指定要序列化的能见度：ANY包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的类，比如String、Integer等会跑出异常
        om.activateDefaultTyping(
                om.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.registerLocalDateTime(om);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // String 的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 添加对事务的支持
        template.setEnableTransactionSupport(true);
        // 刷新配置
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 处理时间类型
     *
     * @param objectMapper 待序列化对象
     */
    private void registerLocalDateTime(ObjectMapper objectMapper) {
        
        // 设置java.util.Date时间类的序列化以及反序列化的格式
        objectMapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));

        JavaTimeModule timeModule = new JavaTimeModule();
        // LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        // LocalDate
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN);
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        // LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN);
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        objectMapper.registerModule(timeModule);

    }

}
