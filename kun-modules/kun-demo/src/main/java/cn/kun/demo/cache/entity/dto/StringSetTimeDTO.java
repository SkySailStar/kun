package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 设值并设置生效时间-传入值
 *
 * @author 廖航
 * @date 2023-01-07 20:30
 */
@Tag(name = "设值并设置生效时间-传入值")
@Data
public class StringSetTimeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "值")
    private String value;

    @Schema(description = "生效时间")
    private Long time;
}
