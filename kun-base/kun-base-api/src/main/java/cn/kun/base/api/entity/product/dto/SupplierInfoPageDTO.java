package cn.kun.base.api.entity.product.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 供应商基本信息分页-传入值
 *
 * @author SkySailStar
 */
@Schema(description = "供应商基本信息分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class SupplierInfoPageDTO extends BasePageDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "供应商编号")
    private String code;

    @Schema(description = "供应商名称")
    private String name;

}