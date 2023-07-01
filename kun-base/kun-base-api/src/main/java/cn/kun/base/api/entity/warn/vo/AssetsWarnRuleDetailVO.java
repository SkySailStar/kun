package cn.kun.base.api.entity.warn.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author eric
 * @date 2023/4/20 16:51
 */
@Schema(description = "一键配置台账-详情-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class AssetsWarnRuleDetailVO extends BaseDetailVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "预警项配置")
    private Long warnItemId;

    @Schema(description = "预警分类编码")
    private String category;

    @Schema(description = "判断类型")
    private String judgeType;

    @Schema(description = "单个判断值")
    private String singleJudgeValue;

    @Schema(description = "起始值")
    private String initialValue;

    @Schema(description = "结束值")
    private String endValue;

    @Schema(description = "预警等级")
    private String warnLevel;

    @Schema(description = "巡检结果判断编码")
    private String judgeCode;

    @Schema(description = "详细分类")
    private String categoryDetails;

    @Schema(description = "备注")
    private String remarks;

}