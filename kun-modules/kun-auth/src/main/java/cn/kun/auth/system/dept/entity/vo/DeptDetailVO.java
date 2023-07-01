package cn.kun.auth.system.dept.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

@Schema(description = "部门详情-返回值")
@Data
public class DeptDetailVO extends BaseDetailVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "部门id")
    private Long id;

    @Schema(description = "所属公司")
    private String company;

    @Schema(description = "所属公司id")
    private String companyId;

    @Schema(description = "所属部门")
    private String parentDept;

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "所属上级")
    private String parentId;

    @Schema(description = "所属上级ids")
    private String parentIds;

    @Schema(description = "备注")
    private String remarks;

}
