package cn.kun.auth.login.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "校验登陆限制-返回值")
@Data
public class CheckResultVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "是否限制登录")
    private Boolean isLogin;

    @Schema(description = "提示信息")
    private String msg;
}
