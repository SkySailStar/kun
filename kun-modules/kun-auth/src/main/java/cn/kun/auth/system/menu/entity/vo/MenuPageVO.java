package cn.kun.auth.system.menu.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 菜单分页列表-返回值
 * 
 * @author eric
 */
@Schema(description = "菜单分页列表-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuPageVO extends BaseSelectVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单id")
    private Long id;

    @Schema(description = "上级菜单Id")
    private Long parentId;

    @Schema(description = "所有上级菜单Id")
    private String parentIds;

    @Schema(description = "上级菜单")
    private String parentMenu;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "链接")
    private String href;

    @Schema(description = "菜单是否可见")
    private String isShow;

    @Schema(description = "系统编码")
    private String serviceCode;

    @Schema(description = "系统编码名称")
    private String serviceName;

    @Schema(description = "权限标识")
    private String permission;
    
    @Schema(description = "备注")
    private String remarks;
}
