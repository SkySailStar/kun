package cn.kun.auth.system.login.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

@Schema(description = "登录限制信息分页-传入值")
@Data
public class LoginLimitPageDTO extends BasePageDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录名")
    private String loginName;

    @Schema(description = "用户ip地址")
    private String ip;

    @Schema(description = "开始时间")
    private LocalDateTime startDate;

    @Schema(description = "结束时间")
    private LocalDateTime endDate;

    @Schema(description = "生效开始时间")
    private LocalDateTime startTime;

    @Schema(description = "生效结束时间")
    private LocalDateTime endTime;

    @Schema(description = "状态")
    private String status;
}
