package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 取值-传入值
 *
 * @author SkySailStar
 * @date 2023-01-07 20:46
 */
@Schema(description = "取值-传入值")
@Data
public class ListGetDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "开始")
    private Long start;

    @Schema(description = "结束")
    private Long end;

}
