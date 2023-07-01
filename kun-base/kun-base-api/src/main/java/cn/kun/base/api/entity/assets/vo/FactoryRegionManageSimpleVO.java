package cn.kun.base.api.entity.assets.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 区域管理返回值
 *
 * @author 胡鹤龄
 * @date 2023-05-16 16:29
 */
@Schema(description = "区域管理-返回值")
@Data
public class FactoryRegionManageSimpleVO extends BaseDetailVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "区域名称")
    private String name;

    @Schema(description = "区域编码")
    private String code;

    @Schema(description = "上级id")
    private Long parentId;

    @Schema(description = "项目名称")
    private String projectNo;

    @Schema(description = "说明")
    private String explain;
}
