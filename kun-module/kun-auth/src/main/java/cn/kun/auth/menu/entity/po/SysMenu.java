package cn.kun.auth.menu.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_menu")
@Schema(name = "SysMenu", description = "菜单表")
public class SysMenu extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "父级编号")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "所有父级编号")
    @TableField("parent_ids")
    private String parentIds;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "类型（0：目录；1：菜单；2：按钮）")
    @TableField("type")
    private String type;

    @Schema(description = "链接（可用可不用）")
    @TableField("href")
    private String href;

    @Schema(description = "图标")
    @TableField("icon")
    @JsonIgnore
    private Long icon;

    @Schema(description = "是否在菜单中显示（0：显示；1：不显示）")
    @TableField("is_show")
    private String isShow;

    @Schema(description = "权限标识")
    @TableField("permission")
    private String permission;

    @Schema(description = "服务代码（用于区分哪些菜单是哪个服务）")
    @TableField("service_code")
    private String serviceCode;
}
