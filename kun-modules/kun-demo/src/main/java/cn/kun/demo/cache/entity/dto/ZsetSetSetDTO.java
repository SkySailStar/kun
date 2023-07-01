package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 有序集合缓存集合设值-传入值
 *
 * @author 廖航
 * @date 2023-03-10 17:48
 */
@Schema(description = "有序集合缓存集合设值-传入值")
@Data
public class ZsetSetSetDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "键")
    private String key;
    
    @Schema(description = "set集合")
    private Set<TypedTuple<Object>> values;
}
