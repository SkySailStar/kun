package cn.kun.auth.company.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 外部公司详情表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_company_detail_outer")
@Schema(name = "SysCompanyDetailOuter", description = "外部公司详情表")
public class SysCompanyDetailOuter extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "外部公司id（关联sys_company_outer表中的id）")
    @TableField("company_outer_id")
    private Long companyOuterId;

    @Schema(description = "公司规模（单位）")
    @TableField("company_size_unit")
    @JsonIgnore()
    private String companySizeUnit;

    @Schema(description = "公司规模（人数）")
    @TableField("company_size_personnel")
    @JsonIgnore()
    private String companySizePersonnel;

    @Schema(description = "公司资金")
    @TableField("company_size")
    @JsonIgnore()
    private BigDecimal companySize;

    @Schema(description = "信用代码")
    @TableField("credit_code")
    @JsonIgnore()
    private String creditCode;


    @Schema(description = "坐标")
    @TableField("coordinate")
    @JsonIgnore()
    private String coordinate;

    @Schema(description = "公司座机")
    @TableField("telephone")
    @JsonIgnore()
    private String telephone;

    @Schema(description = "法人")
    @TableField("juridical_person")
    @JsonIgnore()
    private String juridicalPerson;

    @Schema(description = "法人联系方式")
    @TableField("juridical_phone")
    @JsonIgnore()
    private String juridicalPhone;

    @Schema(description = "法人邮箱")
    @TableField("juridical_email")
    @JsonIgnore()
    private String juridicalEmail;

    @Schema(description = "实际控股人")
    @TableField("controlling_person")
    @JsonIgnore()
    private String controllingPerson;

    @Schema(description = "实际控股人联系方式")
    @TableField("controlling_phone")
    @JsonIgnore()
    private String controllingPhone;

    @Schema(description = "实际控股人邮箱")
    @TableField("controlling_email")
    @JsonIgnore()
    private String controllingEmail;

    @Schema(description = "经营范围")
    @TableField("nature_of_business")
    @JsonIgnore()
    private String natureOfBusiness;

    @Schema(description = "经营开始时间")
    @TableField("operation_start_time")
    @JsonIgnore()
    private LocalDateTime operationStartTime;

    @Schema(description = "经营结束时间")
    @TableField("operation_end_time")
    @JsonIgnore()
    private LocalDateTime operationEndTime;

    @Schema(description = "注册资本")
    @TableField("registered_capital")
    @JsonIgnore()
    private BigDecimal registeredCapital;

    @Schema(description = "实缴资本")
    @TableField("paid_in_capital")
    @JsonIgnore()
    private BigDecimal paidInCapital;

    @Schema(description = "公司简介")
    @TableField("company_profile")
    @JsonIgnore()
    private String companyProfile;
}
