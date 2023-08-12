package cn.kun.base.api.entity.dispatch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/**
 * 任务后续触发时间-传入值
 *
 * @author SkySailStar
 * @date 2023-06-02 13:49
 */
@Schema(description = "任务后续触发时间-传入值")
@Data
public class TaskNextTriggerTimeDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "定时类型")
    @NotBlank(message = "定时类型：不能为空")
    private String scheduleType;
    
    @Schema(description = "定时配置")
    @NotBlank(message = "定时配置：不能为空")
    private String scheduleConf;
    
}
