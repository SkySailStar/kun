package cn.kun.auth.menu.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

@Schema(description = "菜单详情-返回值")
@Data
public class MenuDetailVO extends BaseDetailVO{
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单id")
    private Long id;

    @Schema(description = "上级菜单id")
    private Long parentMenuId;

    @Schema(description = "上级菜单")
    private String parentMenu;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "排序")
    private String sort;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "链接")
    private String href;

    @Schema(description = "图标")
    private Long icon;

    @Schema(description = "菜单是否可见")
    private String isShow;

    @Schema(description = "权限标识")
    private String permission;

    @Schema(description = "服务代码")
    private String serviceCode;

    @Schema(description = "备注")
    private String remarks;
}
