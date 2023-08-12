package cn.kun.system.log.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 运行日志-分页-传入值
 *
 * @author SkySailStar
 */
@Schema(description = "运行日志-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class RunLogPageDTO extends BasePageDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "流水号")
    private String serialNumber;

    @Schema(description = "方法名")
    private String methodName;

    @Schema(description = "日志级别")
    private String logLevel;

    @Schema(description = "运行开始时间")
    private LocalDateTime runStartTime;

    @Schema(description = "运行结束时间")
    private LocalDateTime runEndTime;

    @Schema(description = "运行时间-排序标识;true正序，false倒序")
    private Boolean runTimeAsc;
    
    @Schema(description = "数据来源")
    private String dataSource;
    
}