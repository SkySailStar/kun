package cn.kun.auth.login.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.time.LocalDateTime;

@Schema(description = "用户登录信息分页-返回值")
@Data
public class LoginUserPageVO extends BasePageVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户登录信息id")
    private Long id;

    @Schema(description = "登录名称")
    private String loginName;

    @Schema(description = "登录错误次数")
    private Integer loginErrorNumber;

    @Schema(description = "登录时间")
    private  LocalDateTime loginDate;

    @Schema(description = "登录类型。字典类型：LOGIN_TYPE" + "字典值：" + "手机：PHONE；平板：PAD；网页：PC")
    private String loginType ;

    @Schema(description = "归属地")
    private String ascription;
}
