package cn.kun.auth.role.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "角色-菜单授权查询-返回值")
@Data
public class RoleMenuPermissVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录用户-角色-菜单查询返回值")
    private List<BaseSelectVO> loginUserVo;

    @Schema(description = "当前用户-角色-菜单授权查询返回值")
    private List<BaseSelectVO> roleVo;
}
