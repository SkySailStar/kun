package cn.kun.system.fault.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 故障信息-分页-传入值
 *
 * @author SkySailStar
 * @date 2023-04-11 18:15
 */
@Schema(description = "故障信息-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class FaultInfoPageDTO extends BasePageDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;
    
    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "故障来源分类")
    private String sourceType;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "数据记录开始时间")
    private LocalDateTime recordStartTime;

    @Schema(description = "数据记录结束时间")
    private LocalDateTime recordEndTime;
    
    @Schema(description = "数据记录时间-排序标识;true正序，false倒序")
    private Boolean recordTimeAsc;
}
