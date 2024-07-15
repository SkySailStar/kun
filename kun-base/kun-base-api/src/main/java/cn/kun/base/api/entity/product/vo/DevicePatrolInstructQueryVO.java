package cn.kun.base.api.entity.product.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 挂载装置巡检指令-查询-返回值
 *
 * @author 天航星
 * @date 2023-03-21 10:00
 */
@Schema(description = "挂载装置巡检指令-查询-返回值")
@Data
public class DevicePatrolInstructQueryVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "巡检能力ID")
    private Long PatrolAbilityId;

    @Schema(description = "巡检能力编码")
    private String PatrolAbilityCode;

    @Schema(description = "巡检能力名称")
    private String PatrolAbilityName;

    @Schema(description = "挂载装置类型")
    private String deviceTypeName;

    @Schema(description = "巡检操控类型")
    private String patrolControlType;

    @Schema(description = "巡检操控类型名称")
    private String patrolControlTypeName;

    @Schema(description = "指令参数")
    private String parameter;

    @Schema(description = "是否测试通过;1是，0否")
    private String testFlag;
}
