package cn.kun.auth.login.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;

@Schema(description = "用户登录信息分页-传入值")
@Data
public class LoginUserPageDTO extends BasePageDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录名称")
    private String loginName;

    @Schema(description = "登录错误次数")
    private Integer loginErrorNumber;
}
