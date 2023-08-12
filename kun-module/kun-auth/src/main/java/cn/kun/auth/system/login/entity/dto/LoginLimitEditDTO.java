package cn.kun.auth.system.login.entity.dto;

import cn.hutool.core.date.DateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "用户登陆限制信息修改-传入值")
@Data
public class LoginLimitEditDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录限制信息id")
    @NotNull(message = "登录限制信息id不能为空")
    private Long id;

    @Schema(description = "登录名称")
    @Size(max = 50,message = "登录名称超出长度")
    private String loginName;

    @Schema(description = "用户ip地址")
    @Size(max = 50,message = "用户ip地址超出长度")
    private String ip;

    @Schema(description = "开始时间")
    private LocalDateTime startDate;

    @Schema(description = "结束时间")
    private LocalDateTime endDate;

    @Schema(description = "生效开始时间")
    @NotNull(message = "生效开始时间不能为空")
    private LocalDateTime startTime;

    @Schema(description = "生效结束时间")
    @NotNull(message = "生效结束时间不能为空")
    private LocalDateTime endTime;

    @Schema(description = "状态（0：未开启；1：已开启）")
    @NotBlank(message = "状态不能为空")
    @Size(max = 1,message = "状态超出长度")
    private String status;

    @Schema(description = "登录类型。字典类型：LOGIN_TYPE" + "字典值：" + "手机：PHONE；平板：PAD；网页：PC")
    @Size(max = 50,message = "登录类型超出长度")
    private String loginType;
}
