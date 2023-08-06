package cn.kun.auth.system.project.entity.po;

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
 * 项目表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_project")
@Schema(name = "SysProject", description = "项目表")
public class SysProject extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目编号")
    @TableField("project_no")
    private String projectNo;

    @Schema(description = "上级")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "所有上级ids")
    @TableField("parent_ids")
    private String parentIds;

    @Schema(description = "国家")
    @TableField("country")
    @JsonIgnore()
    private String country;

    @Schema(description = "归属区域")
    @TableField("area_id")
    @JsonIgnore()
    private Long areaId;

    @Schema(description = "区域详情")
    @TableField("area_detail")
    @JsonIgnore()
    private String areaDetail;

    @Schema(description = "项目名称")
    @TableField("name")
    private String name;

    @Schema(description = "坐标")
    @TableField("coordinate")
    @JsonIgnore()
    private String coordinate;

    @Schema(description = "项目进度")
    @TableField("progress")
    @JsonIgnore()
    private String progress;

    @Schema(description = "行业类型")
    @TableField("industry")
    @JsonIgnore()
    private String industry;

    @Schema(description = "项目简称")
    @TableField("abbreviation")
    @JsonIgnore()
    private String abbreviation;

    @Schema(description = "logo")
    @TableField("logo")
    @JsonIgnore()
    private String logo;

    @Schema(description = "ftp完整路径")
    @TableField("path")
    private String  path;
}
