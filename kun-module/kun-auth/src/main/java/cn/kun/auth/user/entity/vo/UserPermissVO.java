package cn.kun.auth.user.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "用户权限-返回值")
@Data
public class UserPermissVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "项目编号列表")
    private List<String> projectNoList;

    @Schema(description = "菜单编号列表")
    private List<Long> menuIdList;

    @Schema(description = "角色编号列表")
    private List<Long> roleIdList;
}
