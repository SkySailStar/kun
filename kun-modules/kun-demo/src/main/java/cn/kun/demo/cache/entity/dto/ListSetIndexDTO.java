package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通过索引设值-传入值
 *
 * @author 廖航
 * @date 2023-01-08 20:51
 */
@Schema(description = "通过索引设值-传入值")
@Data
public class ListSetIndexDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "索引")
    private Long index;

    @Schema(description = "值")
    private Object value;
}
