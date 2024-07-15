package cn.kun.base.core.security.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录-返回值
 *
 * @author 天航星
 * @date 2023-01-07 16:04
 */
@Schema(description = "登录-返回值")
@Data
public class LoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "认证信息")
    private String token;

    @Schema(description = "有效时间（单位：分钟）")
    private Long validTime;
    
}
