package cn.kun.base.api.entity.warn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author eric
 * @date 2023/4/21 9:16
 */
@Schema(description = "台账保存预警规则-添加-传入值")
@Data
public class SaveAssetsWarnRuleDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "台账id")
    @NotNull(message = "台账id不能为空!")
    private Long assetsId;

    @Schema(description = "预警规则信息")
    @NotNull(message = "预警规则信息不能为空!")
    private List<RuleConfigDTO> ruleConfigDTOList;
//    private RuleConfigDTO ruleConfig;

    @Schema(description = "是否添加")
    private String isInsert;

}