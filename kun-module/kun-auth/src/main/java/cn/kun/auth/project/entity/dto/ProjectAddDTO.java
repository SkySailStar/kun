package cn.kun.auth.project.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 项目添加-传入值
 */
@Schema(description = "项目添加-传入值")
@Data
public class ProjectAddDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目名称")
    @NotBlank(message = "项目名称不能为空")
    @Size(max = 50,message = "项目名称超出长度")
    private String name;

    @Schema(description = "项目编号")
    @NotBlank(message = "项目编号不能为空")
    @Size(max = 30,message = "项目编号超出长度")
    private String projectNo;

    @Schema(description = "父级编号")
    private Long parentId;

    @Schema(description = "所有父级编号")
    private String parentIds;

    @Schema(description = "国家")
    @Size(max = 32,message = "国家超出长度")
    private String country;

    @Schema(description = "归属区域")
    @NotNull(message = "归属区域不能为空")
    private Long areaId;

    @Schema(description = "区域详情")
    @NotBlank(message = "区域详情不能为空")
    @Size(max = 100,message = "区域详情超出长度")
    private String areaDetail;

    @Schema(description = "坐标")
    @Size(max = 50,message = "坐标超出长度")
    private String coordinate;

    @Schema(description = "项目进度")
    @NotBlank(message = "项目进度不能为空")
    @Size(max = 32,message = "项目进度超出长度")
    private String progress;

    @Schema(description = "行业")
    @Size(max = 32,message = "行业超出长度")
    private String industry;

    @Schema(description = "简称")
    @Size(max = 32,message = "简称超出长度")
    private String abbreviation;

    @Schema(description = "项目logo")
    private Long logo;

    @Schema(description = "备注")
    private String remarks;

    /* 以下为项目详情数据 */

    @Schema(description = "项目概述")
    @Size(max = 200,message = "项目概述超出长度")
    private String summary;

    @Schema(description = "项目规模")
    @Size(max = 50,message = "项目规模超出长度")
    private String scale;

    @Schema(description = "项目网址")
    @Size(max = 100,message = "项目网址超出长度")
    private String website;

    @Schema(description = "平面区域")
    private String planeArea;

    @Schema(description = "项目性质")
    @Size(max = 32,message = "项目性质超出长度")
    private String saleStatus;

}
