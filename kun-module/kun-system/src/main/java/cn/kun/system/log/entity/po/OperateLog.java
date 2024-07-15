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
 * 操作日志
 * </p>
 *
 * @author 天航星
 * @since 2023-04-09 16:48
 */
@Getter
@Setter
@TableName("operate_log")
@Schema(name = "OperateLog", description = "操作日志")
public class OperateLog extends BaseEntity {

    @Schema(description = "菜单ID;关联sys_menu表id")
    @TableField("menu_id")
    private Long menuId;

    @Schema(description = "流水号")
    @TableField("serial_number")
    private String serialNumber;

    @Schema(description = "操作类型")
    @TableField("operate_type")
    private String operateType;

    @Schema(description = "操作用户名称")
    @TableField("operate_user")
    private String operateUser;

    @Schema(description = "操作时间")
    @TableField("operate_time")
    private LocalDateTime operateTime;

    @Schema(description = "操作数据（用户操作时，传入的数据）")
    @TableField("operate_data")
    private String operateData;

    @Schema(description = "操作内容")
    @TableField("operate_content")
    private String operateContent;

    @Schema(description = "操作标识;FLAG")
    @TableField("operate_flag")
    private String operateFlag;

    @Schema(description = "操作对象类型;OPERATE_TARGET_TYPE")
    @TableField("operate_target_type")
    private String operateTargetType;

    @Schema(description = "操作对象编码")
    @TableField("operate_target_code")
    private String operateTargetCode;

    @Schema(description = "异常编码")
    @TableField("exception_code")
    private String exceptionCode;

    @Schema(description = "异常内容")
    @TableField("exception_msg")
    private String exceptionMsg;
}
