package cn.kun.base.api.entity.patrol.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 巡检能力-查询-传入值
 *
 * @author kuangjc
 * @date 2023-04-12 10:12
 */
@Schema(description = "巡检能力-查询-传入值")
@Data
public class PatrolAbilityQueryDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;
}
