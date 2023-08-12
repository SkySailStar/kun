package cn.kun.auth.system.dept.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

@Schema(description = "部门分页-返回值")
@Data
public class DeptPageVO extends BaseSelectVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "部门id")
    private Long id;

    @Schema(description = "公司名称")
    private String company;

    @Schema(description = "公司id")
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

    @Schema(description = "更新人")
    protected String updateName;

    @Schema(description = "更新日期")
    protected LocalDateTime updateDate;
}
