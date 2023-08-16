package cn.kun.auth.role.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.List;

/**
 * @author eric
 * @date 2023/3/13 16:06
 */
@Schema(description = "用户分页列表-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class RolePersonnelDTO extends BasePageDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色id")
    @NotNull(message = "角色id不能为空!")
    private Long roleId;

    @Schema(description = "公司id")
    private List<String> companyIds;

    @Schema(description = "用户id")
    private List<Long> userIds;
}