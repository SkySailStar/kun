package cn.kun.auth.user.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Schema(description = "角色列表-返回值")
@Data
public class UserRoleListVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色编号")
    private Long roleId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "公司编号")
    private Long parentId;

    @Schema(description = "公司名称")
    private String parentName;
}
