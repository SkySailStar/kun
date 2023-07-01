package cn.kun.demo.crud.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 登录日志-分页-传入值
 *
 * @author 廖航
 */
@Schema(description = "登录日志-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginLogPageDTO extends BasePageDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录账户")
    private String loginName;

    @Schema(description = "登录IP地址")
    private String loginIp;

    @Schema(description = "登录地点")
    private String loginLocation;

    @Schema(description = "登录开始时间")
    private LocalDateTime loginStartTime;

    @Schema(description = "登录结束时间")
    private LocalDateTime loginEndTime;

    @Schema(description = "登录时间-排序标识;true正序，false倒序")
    private Boolean loginTimeAsc;
    
    @Schema(description = "登录方式")
    private String loginWays;

    @Schema(description = "登录业务类型")
    private String loginBusiness;

    @Schema(description = "登录标识")
    private String loginFlag;
    
}