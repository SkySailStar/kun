package cn.kun.auth.system.company.entity.dto;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公司修改-传入值
 *
 * @author 廖航
 */
@Schema(description = "公司修改-传入值")
@Data
public class CompanyEditDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    @Schema(description = "公司名称")
    @NotBlank(message = "公司名称不能为空")
    @Size(max = 100,message = "公司名称超出长度")
    private String name;

    @Schema(description = "父级编号")
    private Long parentId;

    @Schema(description = "所有父级编号")
    @Size(max = 200,message = "所有父级编号超出长度")
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

    @Schema(description = "机构类型")
    @NotBlank(message = "机构类型不能为空")
    @Size(max = 32,message = "机构类型超出长度")
    private String type;

    @Schema(description = "企业logo")
    private Long logo;

    @Schema(description = "排序")
    @Max(value = 999,message = "排序超出长度")
    private Integer sort;

    @Schema(description = "公司性质")
    @Size(max = 50,message = "公司性质超出长度")
    private String natures;

    @Schema(description = "公司官网")
    @Size(max = 100,message = "公司官网超出长度")
    private String web;

    @Schema(description = "备注")
    @Size(max = 255,message = "备注超出长度")
    private String remarks;

    @Schema(description = "行业类型")
    private String industry;

    // 以下为详情数据
    @Schema(description = "信用代码")
    @NotBlank(message = "信用代码:不能为空")
    @Size(max = 100,message = "信用代码超出长度")
    private String creditCode;

    @Schema(description = "公司规模（单位）")
    @Size(max = 32,message = "公司规模（单位）超出长度")
    private String companySizeUnit;

    @Schema(description = "公司规模（人数）")
    @Size(max = 10,message = "公司规模（人数）超出长度")
    private String companySizePersonnel;

    @Schema(description = "公司资金")
    private BigDecimal companySize;

    @Schema(description = "坐标")
    @Size(max = 50,message = "坐标超出长度")
    private String coordinate;

    @Schema(description = "公司座机")
    @Size(max = 20,message = "公司座机超出长度")
    private String telephone;

    @Schema(description = "法人")
    @Size(max = 20,message = "法人超出长度")
    private String juridicalPerson;

    @Schema(description = "法人联系方式")
    @Size(max = 20,message = "法人联系方式超出长度")
    private String juridicalPhone;

    @Schema(description = "法人邮箱")
    @Size(max = 20,message = "法人邮箱超出长度")
    private String juridicalEmail;

    @Schema(description = "实际控股人")
    @Size(max = 20,message = "实际控股人超出长度")
    private String controllingPerson;

    @Schema(description = "实际控股人联系方式")
    @Size(max = 20,message = "实际控股人联系方式超出长度")
    private String controllingPhone;

    @Schema(description = "实际控股人邮箱")
    @Size(max = 20,message = "实际控股人邮箱超出长度")
    private String controllingEmail;

    @Schema(description = "经营范围")
    @Size(max = 32,message = "经营范围超出长度")
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
    @Size(max = 255,message = "公司简介超出长度")
    private String companyProfile;

    @Schema(description = "公司简称")
    @Size(max = 255,message = "公司简称超出长度")
    private String abbreviation;
}
