package cn.kun.auth.user.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 外部用户菜单表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_user_menu_outer")
@Schema(name = "SysUserMenuOuter", description = "外部用户菜单表")
public class SysUserMenuOuter extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "外部用户id（关联sys_user_outer表中的id）")
    @TableField("user_outer_id")
    private Long userOuterId;

    @Schema(description = "菜单id（关联sys_menu表中的id）")
    @TableField("menu_id")
    private Long menuId;
}
