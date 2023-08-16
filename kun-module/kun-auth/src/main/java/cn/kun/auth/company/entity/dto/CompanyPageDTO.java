package cn.kun.auth.company.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 公司分页列表-传入值
 */
@Schema(description = "公司分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class CompanyPageDTO extends BasePageDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "公司名称")
    private String name;

    @Schema(description = "机构类型")
    private String type;

    @Schema(description = "归属区域")
    private Long areaId;

    @Schema(description = "行业类型")
    private String industry;
}
