package cn.kun.base.api.entity.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 产品操作权限验证-传入值
 * </p>
 *
 * @author kuangjc
 * @since 2023-03-06 17:08
 */
@Data
@Schema(description = "产品操作权限验证-传入值")
public class ProductOperatePermissionVeryfyDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目下挂靠产品主键id")
    @NotNull(message = "projectProductId不能为空")
    private Long projectProductId;

    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

}
