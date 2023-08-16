package cn.kun.auth.role.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author eric
 * @date 2023/3/22 10:52
 */
@Schema(description = "角色-人员配置-删除")
@Data
public class RoleUsersDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色id")
    private Long roleId;

    @Schema(description = "用户id")
    private Long userId;
}