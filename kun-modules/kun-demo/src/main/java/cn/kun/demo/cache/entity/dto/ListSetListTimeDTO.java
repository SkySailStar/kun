package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 列表设值并设置生效时间-传入值
 *
 * @author SkySailStar
 * @date 2023-01-08 20:46
 */
@Schema(description = "列表设值并设置生效时间-传入值")
@Data
public class ListSetListTimeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "列表")
    private List<Object> value;

    @Schema(description = "生效时间")
    private Long time;
}
