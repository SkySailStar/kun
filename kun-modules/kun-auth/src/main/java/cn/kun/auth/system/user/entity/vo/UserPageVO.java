package cn.kun.auth.system.user.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;


@Schema(description = "用户分页列表-返回值")
@Data
public class UserPageVO extends BasePageVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名称")
    private String userId;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "登录名")
    private String loginName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "所属公司")
    private String company;

    @Schema(description = "所属公司Id")
    private String companyId;

    @Schema(description = "所属公司名称")
    private String companyName;

    @Schema(description = "所属职位Id")
    private String jobId;

    @Schema(description = "所属职位名称")
    private String jobName;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "备注")
    private String remarks;

}
