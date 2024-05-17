package cn.kun.auth.project.entity.vo;

import com.sevnce.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.time.LocalDateTime;


/**
 * 项目分页列表-返回值
 */
@Schema(description = "项目分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectPageVO extends BaseSelectVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目id")
    private Long id;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称")
    private String name;

    @Schema(description = "上级id")
    private Long parentId;

    @Schema(description = "所有上级")
    private String parentIds;

    @Schema(description = "归属区域")
    private Long areaId;

    @Schema(description = "归属区域名称")
    private String areaName;

    @Schema(description = "坐标")
    private String coordinate;

    @Schema(description = "项目简称")
    private String abbreviation;

    @Schema(description = "行业")
    private String industry;

    @Schema(description = "销售状态")
    private String saleStatus;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "备注1")
    private Boolean flag;

    @Schema(description = "更新人")
    protected String updateName;

    @Schema(description = "更新日期")
    protected LocalDateTime updateDate;
}
