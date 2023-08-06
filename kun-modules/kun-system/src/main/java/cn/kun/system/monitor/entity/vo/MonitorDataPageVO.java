package cn.kun.system.monitor.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 监控数据-分页-返回值
 *
 * @author SkySailStar
 * @date 2023-04-11 18:10
 */
@Schema(description = "监控数据-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class MonitorDataPageVO extends BasePageVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "值")
    private String value;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "项目名称")
    private String projectName;
    
    @Schema(description = "监控项来源分类")
    private String sourceType;

    @Schema(description = "监控项来源分类名称")
    private String sourceTypeName;
    
    @Schema(description = "数据记录时间")
    private LocalDateTime recordTime;
}
