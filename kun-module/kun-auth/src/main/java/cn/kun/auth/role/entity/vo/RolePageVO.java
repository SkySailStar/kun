package cn.kun.auth.role.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

@Schema(description = "角色分页列表-返回值")
@Data
public class RolePageVO extends BasePageVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色id")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色类型")
    private String type;

    @Schema(description = "角色标识")
    private String permission;

    @Schema(description = "公司id")
    private String companyId;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "备注")
    private String remarks;
}
