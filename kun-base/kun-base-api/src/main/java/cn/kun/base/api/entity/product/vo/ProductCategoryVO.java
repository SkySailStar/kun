package cn.kun.base.api.entity.product.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * <p>
 * 产品类型VO
 * </p>
 *
 * @author kuangjc
 * @since 2023-03-06 16:52
 */
@Data
@Schema(description = "产品类型-返回值")
public class ProductCategoryVO extends BaseDetailVO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "产品类别ID")
    private Long id;

    @Schema(description = "上级产品类别Id")
    private Long parentId;

    @Schema(description = "上级产品类别名称")
    private String parentName;

    @Schema(description = "所有上级产品类别Id")
    private String parentIds;

    @Schema(description = "产品类型编码")
    private String code;

    @Schema(description = "产品类型名称")
    private String name;

    @Schema(description = "排序")
    private Integer sort;

}
