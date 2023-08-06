package cn.kun.auth.system.company.entity.po;

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
 * 外部公司表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_company_outer")
@Schema(name = "SysCompanyOuter", description = "外部公司表")
public class SysCompanyOuter extends BaseEntity {

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

    @Schema(description = "归属区域")
    @TableField("area_id")
    @JsonIgnore()
    private Long areaId;

    @Schema(description = "区域详情")
    @TableField("area_detail")
    private String areaDetail;

    @Schema(description = "机构类型（1：公司；2：部门；3:小组；4:其它；5:组织机构）")
    @TableField("type")
    @JsonIgnore()
    private String type;

    @Schema(description = "排序")
    @TableField("sort")
    @JsonIgnore()
    private Integer sort;

    @Schema(description = "公司logo")
    @TableField("logo")
    @JsonIgnore()
    private Long logo;

    @Schema(description = "公司性质")
    @TableField("natures")
    @JsonIgnore()
    private String natures;

    @Schema(description = "公司官网")
    @TableField("web")
    @JsonIgnore()
    private String web;

    @Schema(description = "公司简称")
    @TableField("abbreviation")
    @JsonIgnore()
    private String abbreviation;

    @Schema(description = "国家")
    @TableField("country")
    @JsonIgnore()
    private String country;

    @Schema(description = "行业类型")
    @TableField("industry")
    @JsonIgnore()
    private String industry;

}
