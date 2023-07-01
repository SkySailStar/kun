package cn.kun.base.api.entity.dispatch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 任务-触发-传入值
 *
 * @author 廖航
 * @date 2023-06-01 17:30
 */
@Schema(description = "任务-触发-传入值")
@Data
public class TaskTriggerDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "主键")
    @NotNull(message = "主键：不能为空")
    private Integer id;
    
    @Schema(description = "执行参数")
    private String executorParam;
    
    @Schema(description = "地址列表")
    private List<String> addressList;
}
