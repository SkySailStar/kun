package cn.kun.base.api.entity.dispatch.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 任务-分页-传入值
 *
 * @author 廖航
 * @date 2023-06-05 17:35
 */
@Schema(description = "任务-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class TaskPageDTO extends BasePageDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "执行器ID")
    private Integer executorId;
    
    @Schema(description = "调度状态")
    private Integer triggerStatus;
    
    @Schema(description = "任务描述")
    private String taskDesc;

    @Schema(description = "执行器处理器")
    private String executorHandler;
    
    @Schema(description = "负责人")
    private String author;
    
}
