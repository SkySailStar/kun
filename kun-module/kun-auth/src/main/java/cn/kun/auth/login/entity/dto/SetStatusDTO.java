package cn.kun.auth.login.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Schema(description = "设置登录限制状态-传入值")
@Data
public class SetStatusDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录限制id")
    @NotNull(message = "登录限制id不能为空")
    private Long id;

    @Schema(description = "状态（0：未开启；1：已开启）")
    @NotBlank(message = "登录状态不能为空")
    private String status;
}
