package cn.kun.auth.job.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Schema(description = "公司部门职位信息-返回值")
@Data
public class JobInfoByUserIdVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "职位id")
    private String jobId;

    @Schema(description = "职位名称")
    private String jobName;

    @Schema(description = "部门id")
    private String deptId;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "公司id")
    private String companyId;

    @Schema(description = "公司名称")
    private String companyName;


}
