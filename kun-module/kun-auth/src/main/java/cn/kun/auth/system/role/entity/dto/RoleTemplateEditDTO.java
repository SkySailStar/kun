package cn.kun.auth.system.role.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Schema(description = "角色修改-传入值")
@Data
public class RoleTemplateEditDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @NotBlank(message = "主键不能为空")
    private String id;

    @Schema(description = "项目编号")
    private Integer projectNo;

    @Schema(description = "项目名称")
    private String name;
}
