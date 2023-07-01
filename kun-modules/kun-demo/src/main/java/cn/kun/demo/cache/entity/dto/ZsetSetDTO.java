package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.io.Serial;
import java.io.Serializable;

/**
 * 有序集合缓存设值-传入值
 *
 * @author 廖航
 * @date 2023-03-10 17:42
 */
@Schema(description = "有序集合缓存设值-传入值")
@Data
public class ZsetSetDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private TypedTuple<Object> value;

    /**
     * 得分
     */
    private Double score;
    
}
