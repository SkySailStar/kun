package cn.kun.auth.project.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;

/**
 * @author eric
 * @date 2023/3/27 15:15
 */
@Schema(description = "项目-人员配置返回值")
@Data
public class PersonnelVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "类别（公司、部门、用户）")
    private String type;
    @Schema(description = "类别id（公司id、部门id、用户id）")
    private Long id;
    @Schema(description = "类别名称（公司名称、部门名称、用户名称）")
    private String name;
    @Schema(description = "类别关系id")
    private Long relationId;
    @Schema(description = "类别上级")
    private Long parentId;
    @Schema(description = "类别所有上级")
    private String parentIds;
}