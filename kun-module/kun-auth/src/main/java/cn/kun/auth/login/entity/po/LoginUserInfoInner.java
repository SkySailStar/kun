package cn.kun.auth.login.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p>
 * 内部用户登录信息表
 * </p>
 *
 * @author 胡鹤龄
 * @since 2023-02-27 13:39
 */
@Getter
@Setter
@TableName("login_user_info_inner")
@Schema(name = "LoginUserInfoInner", description = "内部用户登录信息表")
public class LoginUserInfoInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录token")
    @TableField("login_token")
    private String loginToken;

    @Schema(description = "登录名称")
    @TableField("login_name")
    private String loginName;

    @Schema(description = "登录错误次数")
    @TableField("login_error_number")
    private Integer loginErrorNumber;

    @Schema(description = "登录时间")
    @TableField("login_date")
    private LocalDateTime loginDate;

    @Schema(description = "登录类型")
    @TableField("login_type")
    private String loginType;

    @Schema(description = "登录类型型号")
    @TableField("login_type_model")
    private String loginTypeModel;

    @Schema(description = "归属地")
    @TableField("ascription")
    private String ascription;
}
