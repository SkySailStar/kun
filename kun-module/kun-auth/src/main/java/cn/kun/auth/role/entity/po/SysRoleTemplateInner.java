package cn.kun.auth.role.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 内部角色模板表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_role_template_inner")
@Schema(name = "SysRoleTemplateInner", description = "内部角色模板表")
public class SysRoleTemplateInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色模板名称")
    @TableField("name")
    private String name;

    @Schema(description = "角色类型（0：管理员；1：普通角色）")
    @TableField("type")
    private String type;

    @Schema(description = "角色标识")
    @TableField("permission")
    private String permission;
}
