package cn.kun.base.api.entity.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 机器人地图信息-查询-返回值
 *
 * @author 天航星
 * @date 2023-03-27 14:44
 */
@Schema(description = "机器人地图信息-查询-返回值")
@Data
public class MapInfoQueryVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "地图名称")
    private String name;

    @Schema(description = "生效状态")
    private String status;
}
