package cn.kun.auth.role.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "角色添加-传入值")
@Data
public class RoleTemplateAddDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目编号")
    private Integer projectNo;

    @Schema(description = "项目名称")
    private String name;
}
