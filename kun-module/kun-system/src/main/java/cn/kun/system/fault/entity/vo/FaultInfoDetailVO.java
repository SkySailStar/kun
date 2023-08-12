package cn.kun.system.fault.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 故障信息-详情-返回值
 *
 * @author SkySailStar
 * @date 2023-04-12 10:07
 */
@Schema(description = "故障信息-详情-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class FaultInfoDetailVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "信息")
    private String info;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "故障来源分类名称")
    private String sourceTypeName;
    
    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "数据记录时间")
    private LocalDateTime recordTime;
    
}
