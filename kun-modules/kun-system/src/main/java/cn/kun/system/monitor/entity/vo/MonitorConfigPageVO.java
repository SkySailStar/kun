package cn.kun.system.monitor.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 监控项配置-分页-返回值
 *
 * @author SkySailStar
 * @date 2023-04-10 10:14
 */
@Schema(description = "监控项配置-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class MonitorConfigPageVO extends BasePageVO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "类别")
    private String category;

    @Schema(description = "类别名称")
    private String categoryName;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "类型名称")
    private String typeName;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "展示类型")
    private String showType;

    @Schema(description = "展示类型名称")
    private String showTypeName;

    @Schema(description = "启用标识")
    private String enableFlag;

    @Schema(description = "启用标识名称")
    private String enableFlagName;
    
}
