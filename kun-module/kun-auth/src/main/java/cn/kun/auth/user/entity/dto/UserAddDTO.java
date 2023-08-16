package cn.kun.auth.user.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Schema(description = "用户添加-传入值")
@Data
public class UserAddDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名称")
    @NotBlank(message = "用户名称不能为空")
    @Size(max = 10,message = "用户名称超出长度")
    private String name;

    @NotBlank(message = "性别不能为空")
    @Size(max = 32,message = "性别超出长度")
    @Schema(description = "性别")
    private String sex;

    @NotBlank(message = "登录名不能为空")
    @Size(max = 50,message = "登录名超出长度")
    @Schema(description = "登录名")
    private String loginName;

    @Schema(description = "用户密码")
    @NotBlank(message = "用户密码不能为空")
    @Size(max = 18,message = "用户密码超出长度")
    private String password;

    @Schema(description = "内部公司编号")
    private Long companyInnerId;

    @Schema(description = "外部公司编号")
    private Long companyOuterId;

    @Schema(description = "职位id")
    private Long jobId;

    @Schema(description = "状态")
    @Size(max = 64,message = "状态超出长度")
    private String status;

    @Schema(description = "用户头像")
    private Long photo;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "ftp完整路径")
    private String  path;
    /* 以下为详情数据 */

    @Schema(description = "工号")
    @Size(max = 20,message = "工号超出长度")
    private String jobNumber;

    @Schema(description = "邮箱")
    @Size(max = 20,message = "邮箱超出长度")
    private String email;

    @Schema(description = "电话")
    @NotNull(message = "电话不能为空")
    @Size(max = 20,message = "电话超出长度")
    private String phone;

    @Schema(description = "QQ号")
    @Size(max = 20,message = "QQ号超出长度")
    private String qq;

}