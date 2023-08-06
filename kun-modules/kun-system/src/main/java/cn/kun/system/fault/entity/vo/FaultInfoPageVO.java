package cn.kun.system.fault.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 故障信息-分页-返回值
 *
 * @author SkySailStar
 * @date 2023-04-12 10:04
 */
@Schema(description = "故障信息-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class FaultInfoPageVO extends BasePageVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;
    
    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "故障来源分类")
    private String sourceType;

    @Schema(description = "故障来源分类名称")
    private String sourceTypeName;
    
    @Schema(description = "状态")
    private String status;

    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "数据记录时间")
    private LocalDateTime recordTime;
    
}
