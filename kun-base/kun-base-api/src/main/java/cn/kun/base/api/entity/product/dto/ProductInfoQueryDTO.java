package cn.kun.base.api.entity.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 产品基本信息列表-传入值
 *
 * @author kuangjc
 * @date 2023-03-6 15:31
 */
@Schema(description = "产品基本信息-传入值")
@Data
public class ProductInfoQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "产品唯一编号")
    private String productNo;

}
