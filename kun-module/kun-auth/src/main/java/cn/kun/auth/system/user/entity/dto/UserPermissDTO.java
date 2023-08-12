package cn.kun.auth.system.user.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "用户权限保存-传入值")
@Data
public class UserPermissDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "项目编号列表")
    private List<String> projectNoList;

    @Schema(description = "菜单编号列表")
    private List<Long> menuIdList;

    @Schema(description = "角色编号列表")
    private List<Long> roleIdList;
}
