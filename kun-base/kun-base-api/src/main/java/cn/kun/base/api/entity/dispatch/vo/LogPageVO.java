package cn.kun.base.api.entity.dispatch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志-分页-返回值
 *
 * @author 天航星
 */
@Schema(description = "日志-分页-返回值")
@Data
public class LogPageVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "执行器ID")
    private Integer executorId;

    @Schema(description = "执行器名称")
    private String executorName;

    @Schema(description = "任务ID")
    private Integer taskId;
    
    @Schema(description = "调度时间")
    private LocalDateTime triggerTime;
    
    @Schema(description = "调度结果编码")
    private Integer triggerCode;
    
    @Schema(description = "调度详情")
    private String triggerMsg;

    @Schema(description = "执行时间")
    private LocalDateTime executorTime;

    @Schema(description = "执行结果编码")
    private Integer executorCode;

    @Schema(description = "执行结果消息")
    private String executorMsg;

    @Schema(description = "告警状态（0：默认；1：无需告警；2：告警成功；3：告警失败）")
    private Integer alarmStatus;

    @Schema(description = "告警状态名称")
    private String alarmStatusName;
    
}