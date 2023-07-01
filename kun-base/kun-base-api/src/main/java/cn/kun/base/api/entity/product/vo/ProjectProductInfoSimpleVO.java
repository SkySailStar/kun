package cn.kun.base.api.entity.product.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * <p>
 * 项目下挂靠的产品基本信息-返回值
 * </p>
 *
 * @author kuangjc
 * @since 2023-03-15 17:08
 */
@Data
@Schema(description = "项目下挂靠的产品基本信息-返回值")
public class ProjectProductInfoSimpleVO extends BaseDetailVO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "产品编号")
    private String productNo;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "项目编号")
    private String projectNo;

}
