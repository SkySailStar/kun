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
 * 内部登录限制表
 * </p>
 *
 * @author 胡鹤龄
 * @since 2023-02-27 10:37
 */
@Getter
@Setter
@TableName("login_limit_info_inner")
@Schema(name = "LoginLimitInfoInner", description = "内部登录限制表")
public class LoginLimitInfoInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录名称")
    @TableField("login_name")
    private String loginName;

    @Schema(description = "用户ip地址")
    @TableField("user_inner_ip")
    private String userInnerIp;

    @Schema(description = "生效开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;

    @Schema(description = "生效结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;

    @Schema(description = "开始时间")
    @TableField("start_date")
    private LocalDateTime startDate;

    @Schema(description = "结束时间")
    @TableField("end_date")
    private LocalDateTime endDate;

    @Schema(description = "状态")
    @TableField("status")
    private String status;

    @Schema(description = "登录类型")
    @TableField("login_type")
    private String loginType;
}
