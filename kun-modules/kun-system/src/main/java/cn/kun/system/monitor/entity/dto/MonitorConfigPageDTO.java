package cn.kun.system.monitor.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 监控项配置-分页-传入值
 *
 * @author SkySailStar
 * @date 2023-04-10 10:09
 */
@Schema(description = "监控项配置-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class MonitorConfigPageDTO extends BasePageDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "类别")
    private String category;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "展示类型")
    private String showType;

    @Schema(description = "启用标识")
    private String enableFlag;
    
}
