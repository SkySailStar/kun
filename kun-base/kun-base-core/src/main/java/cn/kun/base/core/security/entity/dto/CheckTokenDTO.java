package cn.kun.base.core.security.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 认证信息-传入值
 *
 * @author 廖航
 * @date 2023-01-06 08:53
 */
@Schema(description = "认证信息-传入值")
@Data
public class CheckTokenDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "token值")
    private String token;
}
