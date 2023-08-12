package cn.kun.auth.system.job.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Schema(description = "职位-菜单授权返回值")
@Data
public class JobMenuPermissVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "当前登录职位-菜单授权查询返回值")
    private List<BaseSelectVO> loginUserVo;

    @Schema(description = "当前职位-菜单授权查询返回值")
    private List<BaseSelectVO> jobVo;
}
