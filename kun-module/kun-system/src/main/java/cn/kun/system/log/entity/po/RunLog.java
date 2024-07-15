package cn.kun.system.log.entity.po;

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
 * 运行日志
 * </p>
 *
 * @author 天航星
 * @since 2023-04-09 16:48
 */
@Getter
@Setter
@TableName("run_log")
@Schema(name = "RunLog", description = "运行日志")
public class RunLog extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "流水号")
    @TableField("serial_number")
    private String serialNumber;

    @Schema(description = "方法名")
    @TableField("method_name")
    private String methodName;

    @Schema(description = "日志级别;LOG_LEVEL")
    @TableField("log_level")
    private String logLevel;

    @Schema(description = "运行时间")
    @TableField("run_time")
    private LocalDateTime runTime;

    @Schema(description = "日志内容")
    @TableField("log_content")
    private String logContent;

    @Schema(description = "数据来源;DATA_SOURCE")
    @TableField("data_source")
    private String dataSource;

    @Schema(description = "异常编码")
    @TableField("exception_code")
    private String exceptionCode;

    @Schema(description = "异常消息")
    @TableField("exception_msg")
    private String exceptionMsg;
}
