package cn.kun.auth.company.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 公司分页列表-返回值
 * 
 * @author eric
 */
@Schema(description = "公司分页列表-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class CompanyPageVO extends BaseSelectVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;
    
    @Schema(description = "公司名称")
    private String name;

    @Schema(description = "归属区域")
    private Long areaId;

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "机构类型")
    private String type;

    @Schema(description = "信用代码")
    private String creditCode;

    @Schema(description = "行业类型")
    private String industry;

    @Schema(description = "上级id")
    private Long parentId;

    @Schema(description = "所有上级")
    private String parentIds;

    @Schema(description = "上级名称")
    private String parentName;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "标级是否是子集")
    private Boolean flag;

    @Schema(description = "更新人")
    protected String updateName;

    @Schema(description = "更新日期")
    protected LocalDateTime updateDate;
}
