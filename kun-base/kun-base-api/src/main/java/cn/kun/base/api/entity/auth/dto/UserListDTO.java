package cn.kun.base.api.entity.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.util.List;

/**
 * @author eric
 * @date 2023/3/24 17:37
 */
@Schema(description = "获取用户项目信息")
@Data
public class UserListDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id集合")
    @NotNull
    private List<Long> userIdList;
}