package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author eric
 * @date 2023/3/23 16:13
 */
@Schema(description = "项目信息-返回值")
@Data
public class ProjectParentVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目编号")
    private Long id;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称")
    private String name;

    @Schema(description = "上级项目id")
    private Long parentId;

    @Schema(description = "所有上级项目id")
    private String parentIds;
}