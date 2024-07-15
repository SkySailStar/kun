package cn.kun.demo.crud.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 区域-列表-传入值
 *
 * @author 天航星
 * @date 2023-01-30 15:31
 */
@Schema(description = "区域-列表-传入值")
@Data
public class AreaListDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "行政区域名称")
    private String name;

    @Schema(description = "行政区域类别")
    private String type;
}
