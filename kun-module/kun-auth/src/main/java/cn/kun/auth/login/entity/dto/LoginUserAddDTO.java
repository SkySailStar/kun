package cn.kun.auth.login.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "用户登录信息-传入值")
@Data
public class LoginUserAddDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录token")
    @NotBlank(message = "登录token不能为空")
    @Size(max = 300,message = "登录token超出长度")
    private String loginToken;

    @Schema(description = "登录名称")
    @NotBlank(message = "登录名称不能为空")
    @Size(max = 50,message = "登录名称超出长度")
    private String loginName;

    @Schema(description = "登录错误次数")
    @NotNull(message = "登录错误次数不能为空")
    @Max(value = 999,message = "登录错误次数超出长度")
    private Integer loginErrorNumber;

    @Schema(description = "登录时间")
    @NotNull(message = "登录时间不能为空")
    private LocalDateTime loginDate;

    @Schema(description = "登录类型。字典类型：LOGIN_TYPE" + "字典值：" + "手机：PHONE；平板：PAD；网页：PC")
    @NotBlank(message = "登录类型不能为空")
    @Size(max = 50,message = "登录类型超出长度")
    private String loginType;

    @Schema(description = "登录类型型号")
    @NotBlank(message = "登录类型型号不能为空")
    @Size(max = 50,message = "登录类型型号超出长度")
    private String loginTypeModel;

    @Schema(description = "归属地")
    @NotBlank(message = "归属地不能为空")
    @Size(max = 50,message = "归属地超出长度")
    private String ascription;
}
