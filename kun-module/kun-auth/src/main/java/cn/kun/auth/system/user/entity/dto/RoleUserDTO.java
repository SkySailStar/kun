package cn.kun.auth.system.user.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "角色用户授权-传入值")
@Data
public class RoleUserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色编号")
    @NotNull(message = "角色编号不能为空")
    private Long roleId;

    @Schema(description = "用户编号列表")
    private List<Long> userIdList;
}
