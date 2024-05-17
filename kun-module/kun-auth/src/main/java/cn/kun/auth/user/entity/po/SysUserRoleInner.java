package cn.kun.auth.user.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sevnce.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serial;

/**
 * <p>
 * 内部用户角色表
 * </p>
 *
 * @author 廖航
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_user_role_inner")
@Schema(name = "SysUserRoleInner", description = "内部用户角色表")
public class SysUserRoleInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色编号（关联sys_role_inner表中的id）")
    @TableField("role_inner_id")
    private Long roleInnerId;

    @Schema(description = "用户编号（关联sys_user_inner表中的id）")
    @TableField("user_inner_id")
    private Long userInnerId;
}
