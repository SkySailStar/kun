package cn.kun.base.api.entity.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 离线地图信息-查询-传入值
 *
 * @author 廖航
 * @date 2023-03-27 15:01
 */
@Schema(description = "离线地图信息-查询-传入值")
@Data
public class MapOfflineInfoQueryDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "产品ID")
    private Long productId;

    @Schema(description = "地图ID")
    private Long mapId;

    @Schema(description = "地图名称")
    private String mapName;
}
