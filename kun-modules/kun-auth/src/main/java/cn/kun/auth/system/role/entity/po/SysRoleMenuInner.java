package cn.kun.auth.system.role.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 内部角色菜单表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_role_menu_inner")
@Schema(name = "SysRoleMenuInner", description = "内部角色菜单表")
public class SysRoleMenuInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色编号（关联sys_role_inner表中的id）")
    @TableField("role_inner_id")
    private Long roleInnerId;

    @Schema(description = "菜单编号")
    @TableField("menu_id")
    private Long menuId;
}
