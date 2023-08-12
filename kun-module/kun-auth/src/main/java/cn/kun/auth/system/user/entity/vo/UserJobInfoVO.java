package cn.kun.auth.system.user.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author eric
 * @date 2023/3/28 18:13
 */
@Schema(description = "用户职位-返回值")
@Data
public class UserJobInfoVO {
    @Schema(description = "公司id")
    private Long companyId;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "公司上级")
    private Long companyParentId;

    @Schema(description = "公司所有上级")
    private String companyParentIds;

    @Schema(description = "部门id")
    private Long deptId;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "部门上级")
    private Long deptParentId;

    @Schema(description = "部门所有上级")
    private String deptParentIds;

    @Schema(description = "职位id")
    private Long jobId;

    @Schema(description = "职位名称")
    private String jobName;
}