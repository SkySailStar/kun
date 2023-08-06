package cn.kun.base.api.entity.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 机器人地图信息-查询-传入值
 *
 * @author SkySailStar
 * @date 2023-03-27 13:57
 */
@Schema(description = "机器人地图信息-查询-传入值")
@Data
public class MapInfoQueryDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "产品ID")
    private Long productId;
    
    @Schema(description = "地图名称")
    private String name;
    
    @Schema(description = "生效状态")
    private String status;
}
