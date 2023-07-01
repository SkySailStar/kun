package cn.kun.auth.system.user.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 内部用户菜单表
 * </p>
 *
 * @author 廖航
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_user_menu_inner")
@Schema(name = "SysUserMenuInner", description = "内部用户菜单表")
public class SysUserMenuInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号（关联sys_user_inner表中的id）")
    @TableField("user_inner_id")
    private Long userInnerId;

    @Schema(description = "菜单id（关联sys_menu表中的id）")
    @TableField("menu_id")
    private Long menuId;
}
