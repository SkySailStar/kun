package cn.kun.auth.user.entity.vo;

import com.sevnce.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.util.List;

@Schema(description = "用户-角色授权查询-返回值")
@Data
public class UserRolePermissVO {
    @Serial private static final long serialVersionUID = 1L;

    @Schema(description = "当前登录用户-角色授权查询返回值")
    private List<BaseSelectVO> loginUserVo;

    @Schema(description = "当前用户-角色授权查询返回值")
    private List<BaseSelectVO> userVo;

}
