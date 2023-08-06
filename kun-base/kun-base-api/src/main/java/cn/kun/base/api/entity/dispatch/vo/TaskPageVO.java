package cn.kun.base.api.entity.dispatch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务-分页-返回值
 *
 * @author SkySailStar
 * @date 2023-06-05 18:18
 */
@Schema(description = "任务-分页-返回值")
@Data
public class TaskPageVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Integer id;
    
    @Schema(description = "执行器ID")
    private Integer executorId;
    
    @Schema(description = "执行器名称")
    private String executorName;

    @Schema(description = "任务描述")
    private String taskDesc;

    @Schema(description = "负责人")
    private String author;

    @Schema(description = "调度类型")
    private String scheduleType;
    
    @Schema(description = "运行模式")
    private String glueType;

    @Schema(description = "调度状态;TRIGGER_STATUS")
    private String triggerStatus;

    @Schema(description = "调度状态名称")
    private String triggerStatusName;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
}
