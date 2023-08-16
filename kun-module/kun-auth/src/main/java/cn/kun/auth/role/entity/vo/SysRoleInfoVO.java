package cn.kun.auth.role.entity.vo;


import cn.kun.auth.project.entity.vo.ProjectRedisVO;
import cn.kun.auth.menu.entity.vo.MenuRedisVo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cn.kun.base.core.global.config.LongJsonDeserializer;
import cn.kun.base.core.global.config.LongJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "公司缓存信息-返回值")
@Data
public class SysRoleInfoVO  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色id")
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long roleId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色类型")
    private String roleType;


    @Schema(description = "角色标识")
    private String permission;

    @Schema(description = "内部角色项目权限")
    private List<ProjectRedisVO> roleProjectAuth;

    @Schema(description = "内部角色菜单权限")
    private List<MenuRedisVo> roleMenuAuth;


}
