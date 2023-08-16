package cn.kun.auth.role.entity.vo;

import cn.kun.auth.user.entity.po.UserDetailList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author eric
 * @date 2023/3/13 9:23
 */
@Schema(description = "人员配置-角色-返回值")
@Data
public class RoleDetailListVO {

    @Schema(description = "用户所拥有的公司下所有用户信息")
    private List<UserDetailList> users;

    @Schema(description = "该角色所拥有的用户")
    private List<UserDetailList> roles;

    @Schema(description = "用户所拥有的公司下所有用户信息")
    private List<UserDetailList> usersOuter;

    @Schema(description = "该角色所拥有的用户")
    private List<UserDetailList> rolesOuter;
}