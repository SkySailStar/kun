package cn.kun.auth.user.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author eric
 * @date 2023/3/13 11:39
 */
@Schema(description = "用户详情信息-返回值")
@Data
public class UserDetailList {
    @Schema(description = "主键")
    private String userId;

    @Schema(description = "主键")
    private String value;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "用户登录名称")
    private String loginName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "公司id")
    private Long companyId;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "部门id")
    private Long deptId;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "职位id")
    private Long jobId;

    @Schema(description = "职位名称")
    private String jobName;
}