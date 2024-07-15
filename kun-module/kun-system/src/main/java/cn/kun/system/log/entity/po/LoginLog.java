package cn.kun.system.log.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 登录日志
 * </p>
 *
 * @author 天航星
 * @since 2023-04-09 16:48
 */
@Getter
@Setter
@TableName("login_log")
@Schema(name = "LoginLog", description = "登录日志")
public class LoginLog extends BaseEntity {

    @Schema(description = "登录账户")
    @TableField("login_name")
    private String loginName;

    @Schema(description = "登录IP地址")
    @TableField("login_ip")
    private String loginIp;

    @Schema(description = "登录地点")
    @TableField("login_location")
    private String loginLocation;

    @Schema(description = "登录时间")
    @TableField("login_time")
    private LocalDateTime loginTime;

    @Schema(description = "登录方式;LOGIN_WAYS")
    @TableField("login_ways")
    private String loginWays;

    @Schema(description = "登录业务类型;LOGIN_BUSINESS")
    @TableField("login_business")
    private String loginBusiness;

    @Schema(description = "登录标识;FLAG")
    @TableField("login_flag")
    private String loginFlag;

    @Schema(description = "浏览器类型;BROWSER_TYPE")
    @TableField("browser_type")
    private String browserType;

    @Schema(description = "操作系统;OS_TYPE")
    @TableField("os_type")
    private String osType;

    @Schema(description = "手机型号")
    @TableField("mobile_model")
    private String mobileModel;
}
