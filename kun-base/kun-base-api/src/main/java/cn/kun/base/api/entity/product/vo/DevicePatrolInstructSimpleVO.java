package cn.kun.base.api.entity.product.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 挂载装置巡检指令-简单-返回值
 *
 * @author SkySailStar
 * @date 2023-05-19 15:52
 */
@Schema(description = "挂载装置巡检指令-简单-返回值")
@Data
public class DevicePatrolInstructSimpleVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "巡检能力id;关联patrol_ability表id")
    private Long patrolAbilityId;

    @Schema(description = "挂载装置类型id;关联device_type表id")
    private Long deviceTypeId;

    @Schema(description = "巡检操控类型;字典类型PATROL_CONTROL_TYPE")
    private String patrolControlType;

    @Schema(description = "是否测试通过;1是，0否")
    private String testFlag;

    @Schema(description = "参数")
    private String parameter;
}
