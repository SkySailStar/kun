package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 有序集合缓存-获取排名
 *
 * @author 廖航
 * @date 2023-03-13 10:04
 */
@Schema(description = "有序集合缓存-获取排名")
@Data
public class ZsetRankDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "值")
    private Object value;
}
