package cn.kun.system.area.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 区域-列表-传入值
 *
 * @author SkySailStar
 * @date 2023-01-30 15:31
 */
@Schema(description = "区域-列表-传入值")
@Data
public class AreaListDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;
}
