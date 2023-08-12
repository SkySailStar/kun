package cn.kun.auth.system.dept.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Schema(description = "部门添加-传入值")
@Data
public class DeptAddDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "公司编号")
    @NotNull(message = "公司编号不能为空")
    private Long companyId;

    @Schema(description = "父级编号")
    private Long parentId;

    @Schema(description = "所有父级编号")
    @Size(max = 200,message = "所有父级编号超出长度")
    private String parentIds;

    @Schema(description = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 100,message = "部门名称超出长度")
    private String name;

    @Schema(description = "备注")
    private String remarks;
}