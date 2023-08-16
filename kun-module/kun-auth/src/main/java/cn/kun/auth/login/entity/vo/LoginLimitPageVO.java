package cn.kun.auth.login.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.time.LocalDateTime;

@Schema(description = "用户登陆限制信息分页-返回值")
@Data
public class LoginLimitPageVO extends BasePageVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户ip")
    private String ip;

    @Schema(description = "开始时间")
    private LocalDateTime startDate;

    @Schema(description = "结束时间")
    private LocalDateTime endDate;

    @Schema(description = "生效开始时间")
    private LocalDateTime startTime;

    @Schema(description = "生效结束时间")
    private LocalDateTime endTime;

    @Schema(description = "状态（0：未开启；1：已开启）")
    private String status;

    @Schema(description = "登录类型。字典类型：LOGIN_TYPE" + "字典值：" + "手机：PHONE；平板：PAD；网页：PC")
    private String LoginType;
}
