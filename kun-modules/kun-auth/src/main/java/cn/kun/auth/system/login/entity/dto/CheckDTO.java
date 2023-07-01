package cn.kun.auth.system.login.entity.dto;

import cn.hutool.core.date.DateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "校验登录限制-传入值")
@Data
public class CheckDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ip地址")
    private String ip;

    @Schema(description = "登录名")
    @NotBlank(message = "登录名不能为空")
    private String loginName;

    @Schema(description = "登录类型（字典类型：LOGIN_TYPE" + "字典值：" + "手机：PHONE；平板：PAD；网页：PC")
    @NotBlank(message = "登录类型不能为空")
    private String loginType;
}
