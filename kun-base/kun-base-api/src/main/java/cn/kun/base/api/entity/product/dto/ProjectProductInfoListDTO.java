package cn.kun.base.api.entity.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 项目下挂靠的产品信息表
 * </p>
 *
 * @author kuangjc
 * @since 2023-03-15 17:08
 */
@Data
@Schema(description = "项目下挂靠的产品信息列表-传入值")
public class ProjectProductInfoListDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "产品编号")
    private String productNo;

    @Schema(description = "项目编号")
    private String projectNo;

}
