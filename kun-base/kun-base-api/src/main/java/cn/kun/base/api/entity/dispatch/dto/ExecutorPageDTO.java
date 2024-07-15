package cn.kun.base.api.entity.dispatch.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 执行器-分页-传入值
 *
 * @author 天航星
 * @date 2023-06-05 18:57
 */
@Schema(description = "执行器-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ExecutorPageDTO extends BasePageDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "应用编码")
    private String appCode;
    
    @Schema(description = "应用名称")
    private String appName;
    
}
