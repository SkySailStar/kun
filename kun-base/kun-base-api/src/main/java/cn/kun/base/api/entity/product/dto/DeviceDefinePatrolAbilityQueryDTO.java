package cn.kun.base.api.entity.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 挂载装置定义的巡检能力-查询-传入值
 *
 * @author SkySailStar
 * @date 2023-03-21 11:44
 */
@Schema(description = "挂载装置定义的巡检能力-查询-传入值")
@Data
public class DeviceDefinePatrolAbilityQueryDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "生效状态")
    private String status;

    @Schema(description = "挂载装置定义id")
    private Long deviceDefineId;

    @Schema(description = "巡检类型id;关联patrol_category表id")
    private Long patrolCategoryId;

    @Schema(description = "巡检能力id;关联patrol_ability表id")
    private Long PatrolAbilityId;

}
