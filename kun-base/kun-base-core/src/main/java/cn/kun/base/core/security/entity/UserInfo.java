package cn.kun.base.core.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 * @author 天航星
 */
@Schema(description = "用户信息")
@Data
public class UserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private String id;

    @Schema(description = "账号")
    private String userName;

    @Schema(description = "密码")
    @JsonIgnore
    private String password;

    @Schema(description = "姓名")
    private String nickName;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "状态（true：启用；false：停用）")
    private Boolean status;
}
