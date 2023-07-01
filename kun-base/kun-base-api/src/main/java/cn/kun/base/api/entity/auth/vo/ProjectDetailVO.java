package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目详情返回值
 * @author eric
 * @date 2023/3/24 17:14
 */
@Schema(description = "项目详情-返回值")
@Data
public class ProjectDetailVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目id")
    private Long id;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称")
    private String name;

    @Schema(description = "项目上级id")
    private Long parentId;

    @Schema(description = "项目所有上级")
    private String parentIds;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "归属区域")
    private Long areaId;

    @Schema(description = "区域详情")
    private String areaDetail;

    @Schema(description = "坐标")
    private String coordinate;

    @Schema(description = "项目进度")
    private String progress;

    @Schema(description = "行业")
    private String industry;

    @Schema(description = "简称")
    private String abbreviation;

    @Schema(description = "项目logo")
    private Long logo;

    @Schema(description = "图片路径")
    private String path;

    @Schema(description = "创建人")
    protected String createName;

    @Schema(description = "创建日期")
    protected LocalDateTime createDate;

    @Schema(description = "更新人")
    protected String updateName;

    @Schema(description = "更新日期")
    protected LocalDateTime updateDate;

    @Schema(description = "备注")
    private String remarks;

    /* 以下为项目详情数据 */

    @Schema(description = "项目详情id")
    private Long projectDetailId;

    @Schema(description = "项目概述")
    private String summary;

    @Schema(description = "项目规模")
    private String scale;

    @Schema(description = "项目网址")
    private String website;

    @Schema(description = "平面区域")
    private String planeArea;

    @Schema(description = "项目性质")
    private String saleStatus;
}