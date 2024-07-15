package cn.kun.base.api.entity.product.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 挂载装置定义的巡检能力-查询-返回值
 *
 * @author 天航星
 * @date 2023-03-21 11:47
 */
@Schema(description = "挂载装置定义的巡检能力-查询-返回值")
@Data
public class DeviceDefinePatrolAbilityQueryVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "生效状态")
    @TableField("status")
    private String status;

    @Schema(description = "挂载装置定义")
    private String deviceDefineName;

    @Schema(description = "巡检类别编码")
    private String patrolCategoryCode;

    @Schema(description = "巡检类别名称")
    private String patrolCategoryName;

    @Schema(description = "巡检能力编码")
    private String patrolAbilityCode;

    @Schema(description = "巡检能力名称")
    private String patrolAbilityName;
}
