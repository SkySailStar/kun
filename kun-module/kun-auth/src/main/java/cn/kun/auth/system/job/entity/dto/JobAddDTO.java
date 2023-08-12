package cn.kun.auth.system.job.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Schema(description = "职位添加-传入值")
@Data
public class JobAddDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "公司编号")
    @NotNull(message = "公司编号不能为空")
    private Long companyId;

    @Schema(description = "部门编号")
    @NotNull(message = "部门编号不能为空")
    private Long deptId;

    @Schema(description = "职位名称")
    @NotBlank(message = "职位名称不能为空")
    @Size(max = 50,message = "职位名称超出长度")
    private String name;

    @Schema(description = "备注")
    private String remarks;
}
