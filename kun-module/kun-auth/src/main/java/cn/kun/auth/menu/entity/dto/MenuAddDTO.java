package cn.kun.auth.menu.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Schema(description = "菜单添加-传入值")
@Data
public class MenuAddDTO implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "父级编号")
    private Long parentId;

    @Schema(description = "所有父级编号")
    @Size(max = 200,message = "所有父级编号超出长度")
    private String parentIds;

    @Schema(description = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 100,message = "菜单名称超出长度")
    private String name;

    @Schema(description = "排序")
    @Max(value = 999,message = "排序超出长度")
    private Integer sort;

    @Schema(description = "类型")
    @NotBlank(message = "类型不能为空")
    @Size(max = 64,message = "类型超出长度")
    private String type;

    @Schema(description = "链接")
    @Size(max = 100,message = "链接超出长度")
    private String href;

    @Schema(description = "图标")
    private Long icon;

    @Schema(description = "菜单是否可见")
    @Size(max = 64,message = "菜单是否可见超出长度")
    private String isShow;

    @Schema(description = "权限标识")
    @Size(max = 200,message = "权限标识超出长度")
    private String permission;

    @Schema(description = "服务代码")
    @NotBlank(message = "服务代码不能为空")
    @Size(max = 64,message = "服务代码超出长度")
    private String serviceCode;

    @Schema(description = "备注")
    private String remarks;
}
