package cn.kun.auth.role.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

public class RoleTemplateDetailVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目编号")
    private Integer projectNo;

    @Schema(description = "项目名称")
    private String name;
}
