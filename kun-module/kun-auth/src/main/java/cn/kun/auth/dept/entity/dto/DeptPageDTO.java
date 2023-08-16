package cn.kun.auth.dept.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Schema(description = "部门分页列表-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class DeptPageDTO extends BasePageDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "所属公司")
    private Long companyId;

    @Schema(description = "所属部门")
    private Long parentId;

    @Schema(description = "部门名称")
    private String name;
}
