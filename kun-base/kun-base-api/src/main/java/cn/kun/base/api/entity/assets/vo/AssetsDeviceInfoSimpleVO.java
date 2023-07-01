package cn.kun.base.api.entity.assets.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 台账主设备简单返回对象
 *
 * @author 胡鹤龄
 * @date 2023-05-16 16:07
 */
@Schema(description = "台账主设备-返回值")
@Data
public class AssetsDeviceInfoSimpleVO  extends BaseDetailVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主设备名称")
    private String name;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "父级编号")
    private Long parentId;

    @Schema(description = "主设备编码")
    private String assetsDeviceCode;

    @Schema(description = "主设备类型")
    private String assetsDeviceType;

    @Schema(description = "主设备分类")
    private String assetsDeviceCategory;

    @Schema(description = "主设备状态")
    private String deviceStatus;
}
