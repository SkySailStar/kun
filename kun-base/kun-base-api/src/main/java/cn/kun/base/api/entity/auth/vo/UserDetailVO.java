package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  用户详情返回VO
 * @author eric
 * @date 2023/3/24 17:26
 */
@Schema(description = "用户详情-返回值")
@Data
public class UserDetailVO implements Serializable {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "登录名")
    private String loginName;

    @Schema(description = "性别")
    private String sex;

//    @Schema(description = "用户密码")
//    private String password;

    @Schema(description = "内部公司编号")
    private Long companyInnerId;

    @Schema(description = "外部公司编号")
    private Long companyOuterId;

    @Schema(description = "用户所属公司、部门、职位id")
    private String userParentIds;

    @Schema(description = "部门id")
    private Long deptId;

    @Schema(description = "内部职位id")
    private Long jobInnerId;

    @Schema(description = "外部职位id")
    private Long jobOuterId;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "用户头像")
    private Long photo;

    @Schema(description = "用户头像路径")
    private String path;

    @Schema(description = "创建人")
    protected String createName;

    @Schema(description = "创建日期")
    protected LocalDateTime createDate;

    @Schema(description = "更新人")
    protected String updateName;

    @Schema(description = "更新日期")
    protected LocalDateTime updateDate;

    @Schema(description = "备注")
    private String remarks;

    /* 以下为详情数据 */

    @Schema(description = "工号")
    private String jobNumber;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "QQ号")
    private String qq;
}
