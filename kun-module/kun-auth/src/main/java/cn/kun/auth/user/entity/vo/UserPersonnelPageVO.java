package cn.kun.auth.user.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author eric
 * @date 2023/3/13 15:37
 */

@Schema(description = "人员配置-分页-返回值")
@Data
public class UserPersonnelPageVO {
    @Schema(description = "主键")
    private String userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "用户性别")
    private String sex;

    @Schema(description = "用户登录名")
    private String loginName;

    @Schema(description = "用户手机号")
    private String phone;

    @Schema(description = "职位id")
    private Long jobId;

    @Schema(description = "职位名称")
    private String jobName;
}