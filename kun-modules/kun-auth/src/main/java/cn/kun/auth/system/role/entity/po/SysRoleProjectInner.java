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
 * 内部角色项目表
 * </p>
 *
 * @author 廖航
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_role_project_inner")
@Schema(name = "SysRoleProjectInner", description = "内部角色项目表")
public class SysRoleProjectInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色编号（关联sys_role_inner表中的id）")
    @TableField("role_inner_id")
    private Long roleInnerId;

    @Schema(description = "项目编号")
    @TableField("project_no")
    private String projectNo;
}
