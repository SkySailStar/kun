package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "用户缓存信息-返回值")
@Data
public class SysUserCacheInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "用户信息")
    private SysUserInfoVO userInfo;

    @Schema(description = "职位、部门、公司信息")
    private  List<JobRedisVO> jobInfo;

    @Schema(description = "角色信息")
    private List<RoleInfoByUserIdVO> roleInfo;

    @Schema(description = "用户项目权限信息")
    private List<ProjectParentVO> sysUserProjectAuth;

    @Schema(description = "用户菜单权限信息")
    private List<String> sysUserMenuAuth;


}
