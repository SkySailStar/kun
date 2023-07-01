package cn.kun.base.api.entity.dispatch.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 日志-分页-传入值
 *
 * @author 廖航
 */
@Schema(description = "日志-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class LogPageDTO extends BasePageDTO {

    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "执行器ID")
    private Integer executorId;
    
    @Schema(description = "任务ID")
    private Integer taskId;
    
    @Schema(description = "日志状态（-1：全部；1：成功；2：失败；3：进行中）")
    private Integer logStatus;
    
    @Schema(description = "执行开始时间")
    private LocalDateTime startTime;

    @Schema(description = "执行结束时间")
    private LocalDateTime endTime;
    
}