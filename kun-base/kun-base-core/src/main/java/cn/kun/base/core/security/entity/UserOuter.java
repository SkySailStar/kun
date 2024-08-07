package cn.kun.base.core.security.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 外部用户
 *
 * @author 天航星
 */
@Schema(description = "外部用户")
@Data
public class UserOuter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private String id;

    @Schema(description = "登录名")
    private String loginName;

    @Schema(description = "密码")
    private String password;
}
