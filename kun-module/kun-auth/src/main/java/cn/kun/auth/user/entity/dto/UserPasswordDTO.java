package cn.kun.auth.user.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户修改密码
 *
 * @author eric
 * @date 2023/4/26 15:24
 */
@Schema(description = "用户修改密码-传入值")
@Data
public class UserPasswordDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    @Schema(description = "旧密码")
    @NotBlank(message = "旧密码不能为空")
    @Size(max = 18 ,message = "旧密码超出长度")
    private String password;

    @Schema(description = "新密码")
    @NotBlank(message = "新密码不能为空")
    @Size(max = 18,message = "新密码超出长度")
    private String newPassword;
}