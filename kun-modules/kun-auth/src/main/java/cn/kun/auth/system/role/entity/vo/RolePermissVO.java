package cn.kun.auth.system.role.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "角色权限-返回值")
@Data
public class RolePermissVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色编号")
    private Long RoleId;

    @Schema(description = "项目编号列表")
    private List<String> projectNoList;

    @Schema(description = "菜单编号列表")
    private List<Long> menuIdList;

    @Schema(description = "用户编号列表")
    private List<Long> userIdList;
}