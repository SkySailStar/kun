package cn.kun.base.core.global.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * Json格式化配置
 *
 * @author 天航星
 * @date 2023-02-15 13:55
 */
@Configuration
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JsonConfig {

    @Value("${spring.jackson.time-zone}")
    private String timeZone;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                // 时区
                .setTimeZone(TimeZone.getTimeZone(timeZone))
                // 注册一个序列化和反序列化的model
                .registerModule(javaTimeModule())
                // 配置date格式参数的解析
                .setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATE_PATTERN))
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndRegisterModules();
    }

    @Bean
    public Module javaTimeModule() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)
        ));
        module.addSerializer(new LocalDateSerializer(
                DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)
        ));
        module.addSerializer(new LocalTimeSerializer(
                DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)
        ));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)
        ));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(
                DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)
        ));
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(
                DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)
        ));
        return module;
    }

}
