package cn.kun.base.api.entity.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 挂载装置信息-查询-传入值
 *
 * @author SkySailStar
 * @date 2023-03-17 11:01
 */
@Schema(description = "挂载装置信息-查询-传入值")
@Data
public class DeviceInfoQueryDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "挂载装置唯一编号")
    private String deviceNo;

    @Schema(description = "挂载装置名称")
    private String name;

    @Schema(description = "生效状态")
    private String status;
    
    @Schema(description = "产品ID")
    private Long productId;

    @Schema(description = "挂载装置定义ID")
    private Long deviceDefineId;
    
    @Schema(description = "挂载装置类型ID")
    private Long deviceTypeId;
    
}