package cn.kun.auth.system.job.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Schema(description = "职位分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class JobPageDTO extends BasePageDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "部门id")
    private Long deptId;

    @Schema(description = "职位名称")
    private String name;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "公司Id")
    private Long companyId;

}
