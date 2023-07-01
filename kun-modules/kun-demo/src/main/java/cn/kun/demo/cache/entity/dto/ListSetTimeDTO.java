package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 设值并设置生效时间-传入值
 *
 * @author 廖航
 * @date 2023-01-08 20:28
 */
@Schema(description = "设值并设置生效时间-传入值")
@Data
public class ListSetTimeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "值")
    private Object value;

    @Schema(description = "生效时间")
    private Long time;
}
