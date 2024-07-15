package cn.kun.system.log.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志-添加-传入值
 *
 * @author 天航星
 */
@Schema(description = "登录日志-添加-传入值")
@Data
public class LoginLogAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录账户")
    @NotBlank(message = "登录账户：不能为空")
    @Size(max = 64, message = "登录账户：长度不能大于64")
    private String loginName;

    @Schema(description = "登录IP地址")
    @Size(max = 64, message = "登录IP地址：长度不能大于64")
    private String loginIp;

    @Schema(description = "登录地点")
    @Size(max = 255, message = "登录地点：长度不能大于255")
    private String loginLocation;

    @Schema(description = "登录时间")
    @NotNull(message = "登录时间：不能为空")
    private LocalDateTime loginTime;

    @Schema(description = "登录方式;LOGIN_WAYS")
    @Size(max = 64, message = "登录方式：长度不能大于64")
    private String loginWays;

    @Schema(description = "登录业务类型;LOGIN_BUSINESS")
    @Size(max = 64, message = "登录业务类型：长度不能大于64")
    private String loginBusiness;

    @Schema(description = "登录标识;FLAG")
    @Size(max = 1, message = "登录标识：长度不能大于1")
    private String loginFlag;

    @Schema(description = "浏览器类型;BROWSER_TYPE")
    @Size(max = 64, message = "浏览器类型：长度不能大于64")
    private String browserType;

    @Schema(description = "操作系统;OS_TYPE")
    @Size(max = 64, message = "操作系统：长度不能大于64")
    private String osType;

    @Schema(description = "手机型号")
    @Size(max = 64, message = "手机型号：长度不能大于64")
    private String mobileModel;
    
}