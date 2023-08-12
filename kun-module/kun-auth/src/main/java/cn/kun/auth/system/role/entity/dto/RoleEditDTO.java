package cn.kun.auth.system.role.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Schema(description = "角色修改-传入值")
@Data
public class RoleEditDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 100, message = "角色名称超出长度")
    private String name;

    @Schema(description = "角色类型。字典类型：ROLE_TYPE" + "字典值：" + "管理员：MANAGE；普通：ORDINARY；超级管理员：SUPER_MANAGE" + "其他：OTHER；")
    @Size(max = 64, message = "角色类型超出长度")
    @NotBlank(message = "角色类型不能为空")
    private String type;

    @Schema(description = "角色标识")
    @Size(max = 200, message = "角色标识超出长度")
    private String permission;

    @Schema(description = "公司编号")
    @NotNull(message = "公司id不能为空")
    private Long companyId;

    @Schema(description = "备注")
    private String remarks;
}
