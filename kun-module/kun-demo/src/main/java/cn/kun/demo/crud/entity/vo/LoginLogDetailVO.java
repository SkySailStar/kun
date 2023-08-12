package cn.kun.demo.crud.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 登录日志-详情-返回值
 *
 * @author SkySailStar
 */
@Schema(description = "登录日志-详情-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginLogDetailVO extends BaseDetailVO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录账户")
    private String loginName;

    @Schema(description = "登录IP地址")
    private String loginIp;

    @Schema(description = "登录地点")
    private String loginLocation;

    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

    @Schema(description = "登录方式名称")
    private String loginWaysName;

    @Schema(description = "登录业务类型名称")
    private String loginBusinessName;

    @Schema(description = "登录标识名称")
    private String loginFlagName;

    @Schema(description = "浏览器类型名称")
    private String browserTypeName;

    @Schema(description = "操作系统名称")
    private String osTypeName;

    @Schema(description = "手机型号")
    private String mobileModel;

}