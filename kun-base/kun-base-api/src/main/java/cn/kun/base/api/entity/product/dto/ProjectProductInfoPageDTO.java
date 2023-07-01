package cn.kun.base.api.entity.product.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import cn.kun.base.core.global.enums.ProductBuildStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * <p>
 * 项目下挂靠的产品信息表
 * </p>
 *
 * @author kuangjc
 * @since 2023-03-15 17:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "项目下挂靠的产品信息分页-传入值")
public class ProjectProductInfoPageDTO extends BasePageDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "产品编号")
    private String productNo;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "产品定义Id")
    private Long productDefineId;

    @Schema(description = "产品类别第一级ID")
    private Long productFirstCategoryId;

    @Schema(description = "产品类别第二级ID")
    private Long productSecondCategoryId;

    @Schema(description = "产品类别第三级ID")
    private Long productThirdCategoryId;

    @Schema(description = "产品建设状态")
    private ProductBuildStatusEnum buildStatus;

    @Schema(description = "项目编号列表")
    private List<String> projectNoList;
}
