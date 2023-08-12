package cn.kun.auth.system.user.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "用户角色授权-传入值")
@Data
public class UserRoleDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "角色编号列表")
    private List<Long> RoleIdList;
}
