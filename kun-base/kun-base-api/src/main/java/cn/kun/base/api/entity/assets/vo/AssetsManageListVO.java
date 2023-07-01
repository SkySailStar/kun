package cn.kun.base.api.entity.assets.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * @author eric
 * @date 2023/4/6 16:40
 */
@Schema(description = "台账分页-返回值")
@Data
public class AssetsManageListVO extends BaseDetailVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "台账模板")
    private Long assetsTemplateId;

    @Schema(description = "台账模板名称")
    private String templateName;

    @Schema(description = "台账分类")
    private Long classificationId;

    @Schema(description = "台账分类名称")
    private String classificationName;

    @Schema(description = "台账名称")
    private String name;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "所属算法")
    private Long aiId;

    @Schema(description = "所属算法名称")
    private Long aiName;

    @Schema(description = "主设备")
    private Long assetsDeviceId;

    @Schema(description = "图片id")
    private Long fileId;

    @Schema(description = "是否巡检")
    private String status;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "json")
    private String json;

    @Schema(description = "设备状态")
    private String deviceStatus;

    @Schema(description = "台账类型")
    private String type;

}