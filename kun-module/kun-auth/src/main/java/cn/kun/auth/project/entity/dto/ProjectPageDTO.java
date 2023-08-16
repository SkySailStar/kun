package cn.kun.auth.project.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 项目分页列表-传入值
 */
@Schema(description = "项目分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectPageDTO extends BasePageDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称")
    private String name;

    @Schema(description = "简称")
    private String abbreviation;

    @Schema(description = "归属区域")
    private Long areaId;

    @Schema(description = "行业")
    private String industry;

    @Schema(description = "项目性质")
    private String saleStatus;
}
