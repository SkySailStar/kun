package cn.kun.base.api.entity.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 产品基本信息-查询-返回值
 *
 * @author kuangjc
 * @date 2023-03-17 11:19
 */
@Schema(description = "产品信息-查询-返回值")
@Data
public class ProductInfoQueryVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "产品名称")
    private String name;

    @Schema(description = "产品编号")
    private String productNo;

}
