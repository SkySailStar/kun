package cn.kun.base.api.entity.warn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author eric
 * @date 2023/4/21 9:51
 */
@Schema(description = "预警规则-传入值")
@Data
public class RuleConfigDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "预警项配置")
    @NotNull(message = "预警项配置不能为空")
    private Long warnItemId;

    @Schema(description = "预警分类编码")
    @Size(max = 32, message = "预警分类编码：长度不能大于32")
    private String category;

    @Schema(description = "判断类型")
    @NotBlank(message = "判断类型：不能为空")
    @Size(max = 32, message = "判断类型：长度不能大于32")
    private String judgeType;

    @Schema(description = "单个判断值")
    @Size(max = 20, message = "单个判断值：长度不能大于20")
    private String singleJudgeValue;

    @Schema(description = "起始值")
    @Size(max = 100, message = "起始值：长度不能大于100")
    private String initialValue;

    @Schema(description = "结束值")
    @Size(max = 100, message = "结束值：长度不能大于100")
    private String endValue;

    @Schema(description = "预警等级")
    @NotBlank(message = "预警等级：不能为空")
    @Size(max = 32, message = "预警等级：长度不能大于32")
    private String warnLevel;

    @Schema(description = "巡检结果判断编码")
    @Size(max = 32, message = "巡检结果判断编码：长度不能大于32")
    private String judgeCode;

    @Schema(description = "详细分类")
    @Size(max = 200, message = "详细分类：长度不能大于200")
    private String categoryDetails;

    @Schema(description = "预警类型编码")
    @Size(max = 32, message = "预警类型编码：长度不能大于32")
    private String warnCategoryCode;

    @Schema(description = "告警类型名称")
    @Size(max = 32, message = "告警类型名称：长度不能大于32")
    private String warnTypeName;

    @Schema(description = "是否删除")
    @Size(max = 1, message = "是否删除：长度不能大于1")
    private String delFlag;

    @Schema(description = "备注")
    @Size(max = 255, message = "备注：长度不能大于255")
    private String remarks;
}