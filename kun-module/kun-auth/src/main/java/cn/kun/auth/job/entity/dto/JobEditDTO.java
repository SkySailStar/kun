package cn.kun.auth.job.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;


@Schema(description = "职位修改-传入值")
@Data
public class JobEditDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    @Schema(description = "公司编号")
    @NotNull(message = "公司编号不能为空")
    private Long companyId;

    @Schema(description = "部门编号")
    @NotNull(message = "部门编号不能为空")
    private Long deptId;

    @Schema(description = "职位名称")
    @Size(max = 50,message = "职位名称超出长度")
    private String name;

    @Schema(description = "备注")
    private String remarks;

}
