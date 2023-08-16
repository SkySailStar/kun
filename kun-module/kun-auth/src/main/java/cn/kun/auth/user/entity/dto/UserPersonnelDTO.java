package cn.kun.auth.user.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.util.List;

/**
 * @author eric
 * @date 2023/3/13 15:40
 */

@Schema(description = "用户分页列表-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPersonnelDTO extends BasePageDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目编号")
    @NotBlank(message = "项目编号不能为空")
    private String projectNo;

    @Schema(description = "公司编号")
    private List<String> companyIds;

}