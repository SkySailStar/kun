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
 * 内部角色表
 * </p>
 *
 * @author 廖航
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_role_inner")
@Schema(name = "SysRoleInner", description = "内部角色表")
public class SysRoleInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色名称")
    @TableField("name")
    private String name;

    @Schema(description = "角色类型（0：管理员；1：普通角色;2:超级管理员）")
    @TableField("type")
    private String type;

    @Schema(description = "所属公司")
    @TableField("company_inner_id")
    private Long companyInnerId;

    @Schema(description = "角色标识")
    @TableField("permission")
    private String permission;
    
    @Schema(description = "备注")
    private String remarks;
}
