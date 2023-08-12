package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除-传入值
 *
 * @author SkySailStar
 * @date 2023-01-08 20:56
 */
@Schema(description = "删除-传入值")
@Data
public class ListDelDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "移除多少个")
    private Long count;

    @Schema(description = "值")
    private Object value;
}
