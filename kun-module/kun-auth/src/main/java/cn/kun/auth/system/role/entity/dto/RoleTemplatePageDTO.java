package cn.kun.auth.system.role.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Schema(description = "角色模板分页列表-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleTemplatePageDTO extends BasePageDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目编号")
    private Integer projectNo;

    @Schema(description = "项目名称")
    private String name;
}
