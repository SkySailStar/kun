package cn.kun.auth.user.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sevnce.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Size;
import java.io.Serial;

/**
 * <p>
 * 外部用户表
 * </p>
 *
 * @author 廖航
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_user_outer")
@Schema(name = "SysUserOuter", description = "外部用户表")
public class SysUserOuter extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "所属外部公司（关联sys_company_outer表中的id）")
    @TableField("company_outer_id")
    private Long companyOuterId;

    @Schema(description = "登录名")
    @TableField("login_name")
    @Size(max = 50)
    private String loginName;

    @Schema(description = "密码")
    @TableField("password")
    @Size(max = 100)
    private String password;

    @Schema(description = "名称")
    @TableField("name")
    @Size(max = 50)
    private String name;

    @Schema(description = "性别")
    @TableField("sex")
    @Size(max = 32)
    private String sex;

    @Schema(description = "状态（0：正常；1注销；2：黑名单）")
    @TableField("status")
    @Size(max = 32)
    private String status;

    @Schema(description = "用户头像")
    @TableField("photo")
    private Long photo;

    @Schema(description = "ftp完整路径")
    @TableField("path")
    private String  path;
}
