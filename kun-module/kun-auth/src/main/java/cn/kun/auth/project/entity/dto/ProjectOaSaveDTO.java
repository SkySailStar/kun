package cn.kun.auth.project.entity.dto;

import cn.kun.auth.company.entity.dto.CompanyAddDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Schema(description = "项目流程-传入值")
@Data
public class ProjectOaSaveDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目新增dto对象")
    private ProjectAddDTO projectAddd_dto;

    @Schema(description = "公司新增dto对象")
    private CompanyAddDTO companyAdd_dto;
}
