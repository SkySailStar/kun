package cn.kun.base.api.entity.product.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 供应商基本信息分页-返回值
 *
 * @author SkySailStar
 */
@Schema(description = "供应商基本信息分页-返回值")
@Data
public class SupplierInfoPageVO extends BasePageVO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "供应商ID")
    private Long id;

    @Schema(description = "供应商编号")
    private String code;

    @Schema(description = "供应商名称")
    private String name;

}