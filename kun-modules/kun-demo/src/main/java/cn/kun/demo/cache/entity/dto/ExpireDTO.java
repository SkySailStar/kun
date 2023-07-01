package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 设置缓存失效时间-传入值
 *
 * @author 廖航
 * @date 2023-01-06 09:40
 */
@Schema(description = "设置缓存失效时间-传入值")
@Data
public class ExpireDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键值")
    private String key;

    @Schema(description = "失效时间（秒）")
    private Long time;
}
