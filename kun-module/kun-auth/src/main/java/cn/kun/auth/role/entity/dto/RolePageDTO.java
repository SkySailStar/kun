package cn.kun.auth.role.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 角色分页列表-传入值
 */
@Schema(description = "角色分页列表-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class RolePageDTO extends BasePageDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色类型")
    private String type;

}
