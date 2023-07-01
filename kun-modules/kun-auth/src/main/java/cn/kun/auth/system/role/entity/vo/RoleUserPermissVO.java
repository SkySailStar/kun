package cn.kun.auth.system.role.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "角色-用户授权查询-返回值")
@Data
public class RoleUserPermissVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录用户-角色授权查询返回值")
    private PersonnelRoleVO loginUserVo;

    @Schema(description = "当前用户-角色授权查询返回值")
    private PersonnelRoleVO userVo;
}
