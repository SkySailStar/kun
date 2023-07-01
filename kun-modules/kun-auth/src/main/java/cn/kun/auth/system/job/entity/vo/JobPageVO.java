package cn.kun.auth.system.job.entity.vo;


import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

@Schema(description = "职位分页-返回值")
@Data
public class JobPageVO extends BasePageVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "职位id")
    private Long id;

    @Schema(description = "公司id")
    private String companyId;

    @Schema(description = "公司名称")
    private String company;

    @Schema(description = "部门名称id")
    private String deptId;

    @Schema(description = "部门名称")
    private String dept;

    @Schema(description = "职位名称")
    private String job;

    @Schema(description = "备注")
    private String remarks;

}
