package cn.kun.auth.user.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Size;
import java.io.Serial;

/**
 * <p>
 * 内部用户详细表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_user_detail_inner")
@Schema(name = "SysUserDetailInner", description = "内部用户详细表")
public class SysUserDetailInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "内部用户（关联sys_user_inner表中的id）")
    @TableField("user_inner_id")
    private Long userInnerId;

    @Schema(description = "工号")
    @TableField("job_number")
    @Size(max = 20)
    private String jobNumber;

    @Schema(description = "邮箱")
    @TableField("email")
    @Size(max = 50)
    private String email;

    @Schema(description = "QQ号")
    @TableField("qq")
    @Size(max = 20)
    private String qq;

    @Schema(description = "电话")
    @TableField("phone")
    @Size(max = 20)
    private String phone;
}
