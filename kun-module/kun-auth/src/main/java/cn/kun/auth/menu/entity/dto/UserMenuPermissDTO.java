package cn.kun.auth.menu.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Schema(description = "用户菜单树形列表-传入值")
@Data
public class UserMenuPermissDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "角色编号")
    private Long roleId;

    @Schema(description = "职位编号")
    private Long jobId;

    @Schema(description = "服务代码")
    @Size(max = 64,message = "服务代码超出长度")
    private String serviceCode;
}
