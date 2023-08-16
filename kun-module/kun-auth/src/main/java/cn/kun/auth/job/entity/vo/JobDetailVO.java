package cn.kun.auth.job.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

@Schema(description = "职位详情-返回值")
@Data
public class JobDetailVO extends BaseDetailVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "职位id")
    private Long id;

    @Schema(description = "部门id")
    private String deptId;

    @Schema(description = "部门名称")
    private String dept;

    @Schema(description = "公司id")
    private String company;

    @Schema(description = "公司名称")
    private String companyId;

    @Schema(description = "职位名称")
    private String name;

    @Schema(description = "备注")
    private String remarks;

}
