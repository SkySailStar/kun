package cn.kun.system.log.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 操作日志-分页-传入值
 *
 * @author SkySailStar
 */
@Schema(description = "操作日志-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class OperateLogPageDTO extends BasePageDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单ID")
    private Long menuId;

    @Schema(description = "流水号")
    private String serialNumber;

    @Schema(description = "操作类型")
    private String operateType;

    @Schema(description = "操作用户名称")
    private String operateUser;

    @Schema(description = "操作开始时间")
    private LocalDateTime operateStartTime;

    @Schema(description = "操作结束时间")
    private LocalDateTime operateEndTime;
    
    @Schema(description = "操作时间-排序标识;true正序，false倒序")
    private Boolean operateTimeAsc;
    
    @Schema(description = "操作内容")
    private String operateContent;

    @Schema(description = "操作标识")
    private String operateFlag;

    @Schema(description = "操作对象类型")
    private String operateTargetType;
    
}