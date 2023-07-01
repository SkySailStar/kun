package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 列表设值-传入值
 *
 * @author 廖航
 * @date 2023-01-08 20:40
 */
@Schema(description = "列表设值-传入值")
@Data
public class ListSetListDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "列表值")
    private List<Object> value;
}
