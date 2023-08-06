package cn.kun.base.api.entity.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 挂载装置巡检指令-查询-传入值
 *
 * @author SkySailStar
 * @date 2023-03-21 09:58
 */
@Schema(description = "挂载装置巡检指令-查询-传入值")
@Data
public class DevicePatrolInstructQueryDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "巡检能力ID")
    private Long patrolAbilityId;

    @Schema(description = "挂载装置类型ID")
    private Long deviceTypeId;

    @Schema(description = "巡检操控类型;字典类型PATROL_CONTROL_TYPE")
    private String patrolControlType;
    
    @Schema(description = "是否测试通过;1是，0否")
    private String testFlag;
}
