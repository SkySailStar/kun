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
 * 内部角色模板项目表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_role_template_project_inner")
@Schema(name = "SysRoleTemplateProjectInner", description = "内部角色模板项目表")
public class SysRoleTemplateProjectInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色模板编号（关联sys_role_template_inner表中的id）")
    @TableField("role_template_inner_id")
    private Long roleTemplateInnerId;

    @Schema(description = "项目id（关联sys_project表中的id）")
    @TableField("project_id")
    private Long projectId;
}
