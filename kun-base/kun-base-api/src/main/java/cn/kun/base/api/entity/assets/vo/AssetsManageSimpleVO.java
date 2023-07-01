package cn.kun.base.api.entity.assets.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * @author eric
 * @date 2023/5/16 16:40
 */
@Schema(description = "台账信息-返回值")
@Data
public class AssetsManageSimpleVO extends BaseDetailVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "台账模板")
    private Long assetsTemplateId;

    @Schema(description = "台账分类")
    private Long classificationId;

    @Schema(description = "台账名称")
    private String name;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "所属算法")
    private Long aiId;

    @Schema(description = "台账状态")
    private String status;

    @Schema(description = "台账状态名称")
    private String statusName;

    @Schema(description = "台账单位")
    private String unit;

    @Schema(description = "主设备编号")
    private Long assetsDeviceId;

    @Schema(description = "主设备名称")
    private String assetsDeviceName;

    @Schema(description = "设备状态")
    private String deviceStatus;

    @Schema(description = "台账类型；字典")
    private String type;

    @Schema(description = "区域编号")
    private Long factoryRegionManageId;

    @Schema(description = "区域名称")
    private String factoryRegionManageName;

}