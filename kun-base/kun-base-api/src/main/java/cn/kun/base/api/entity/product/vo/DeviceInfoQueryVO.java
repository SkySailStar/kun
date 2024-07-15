package cn.kun.base.api.entity.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 挂载装置信息-查询-返回值
 *
 * @author 天航星
 * @date 2023-03-17 11:19
 */
@Schema(description = "挂载装置信息-查询-返回值")
@Data
public class DeviceInfoQueryVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "挂载装置唯一编号")
    private String deviceNo;
    
    @Schema(description = "挂载装置名称")
    private String name;

    @Schema(description = "挂载装置定义名称")
    private String deviceDefineName;

    @Schema(description = "挂载装置类型")
    private String deviceTypeName;

    @Schema(description = "产品名称")
    private String productName;
    
    @Schema(description = "生效状态")
    private String status;
    
}
