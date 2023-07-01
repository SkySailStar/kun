package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "公司部门职位信息-返回值")
@Data
public class RoleInfoByUserIdVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "角色id")
    private String roleId;

    @Schema(description = "角色名称")
    private String roleName;

    // 角色类型（0：管理员；1：普通角色）
    @Schema(description = "角色类型")
    private String roleType;


    @Schema(description = "角色标识")
    private String permission;
}
