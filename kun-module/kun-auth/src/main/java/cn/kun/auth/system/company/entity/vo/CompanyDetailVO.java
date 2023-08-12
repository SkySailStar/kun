package cn.kun.auth.system.company.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "公司详情-返回值")
@Data
public class CompanyDetailVO extends BaseDetailVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "公司id")
    private Long id;

    @Schema(description = "公司名称")
    private String name;

    @Schema(description = "父级编号")
    private Long parentId;

    @Schema(description = "所有父级编号")
    private String parentIds;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "归属区域")
    private Long areaId;

    @Schema(description = "行业类型")
    private String industry;

    @Schema(description = "区域详情")
    private String areaDetail;

    @Schema(description = "机构类型")
    private String type;

    @Schema(description = "企业logo")
    private Long logo;

    @Schema(description = "logo路径")
    private String path;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "公司简称")
    private String natures;

    @Schema(description = "公司官网")
    private String web;
    
    @Schema(description = "备注")
    private String remarks;

    // 以下为详情数据
    @Schema(description = "信用代码")
    private String creditCode;

    @Schema(description = "公司规模（单位）")
    private String companySizeUnit;

    @Schema(description = "公司规模（人数）")
    private String companySizePersonnel;

    @Schema(description = "公司资金")
    private BigDecimal companySize;

    @Schema(description = "坐标")
    private String coordinate;

    @Schema(description = "公司座机")
    private String telephone;

    @Schema(description = "法人")
    private String juridicalPerson;

    @Schema(description = "法人联系方式")
    private String juridicalPhone;

    @Schema(description = "法人邮箱")
    private String juridicalEmail;

    @Schema(description = "实际控股人")
    private String controllingPerson;

    @Schema(description = "实际控股人联系方式")
    private String controllingPhone;

    @Schema(description = "实际控股人邮箱")
    private String controllingEmail;

    @Schema(description = "经营范围")
    private String natureOfBusiness;

    @Schema(description = "经营开始时间")
    private LocalDateTime operationStartTime;

    @Schema(description = "经营结束时间")
    private LocalDateTime operationEndTime;

    @Schema(description = "注册资本")
    private BigDecimal registeredCapital;

    @Schema(description = "实缴资本")
    private BigDecimal paidInCapital;

    @Schema(description = "公司简介")
    private String companyProfile;

    @Schema(description = "公司简称")
    private String abbreviation;
}
