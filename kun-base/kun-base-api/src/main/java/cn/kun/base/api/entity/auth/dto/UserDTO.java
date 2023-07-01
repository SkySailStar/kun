package cn.kun.base.api.entity.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * @author eric
 * @date 2023/3/23 16:11
 */
@Schema(description = "获取用户项目信息")
@Data
public class UserDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户标识:true内部、false外部")
    private Boolean characteristic;
}