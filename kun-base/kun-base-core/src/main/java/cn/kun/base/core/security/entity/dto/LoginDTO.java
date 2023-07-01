package cn.kun.base.core.security.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/**
 * 登录-传入值
 *
 * @author 廖航
 */
@Schema(description = "登录-传入值")
@Data
public class LoginDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录名")
    @NotBlank(message = "登录名不能为空")
    private String loginName;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "有效时间（单位：分钟）")
    private Long validTime;
}