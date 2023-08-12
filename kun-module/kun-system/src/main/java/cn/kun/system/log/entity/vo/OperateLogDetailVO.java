package cn.kun.system.log.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 操作日志-详情-返回值
 *
 * @author SkySailStar
 */
@Schema(description = "操作日志-详情-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class OperateLogDetailVO extends BaseDetailVO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "功能名称")
    private String menuName;

    @Schema(description = "流水号")
    private String serialNumber;

    @Schema(description = "操作类型名称")
    private String operateTypeName;

    @Schema(description = "操作用户名称")
    private String operateUser;

    @Schema(description = "操作时间")
    private LocalDateTime operateTime;

    @Schema(description = "操作数据（用户操作时，传入的数据）")
    private String operateData;

    @Schema(description = "操作内容")
    private String operateContent;

    @Schema(description = "操作标识名称")
    private String operateFlagName;

    @Schema(description = "操作对象类型名称")
    private String operateTargetTypeName;

    @Schema(description = "操作对象编码")
    private String operateTargetCode;

    @Schema(description = "异常编码")
    private String exceptionCode;

    @Schema(description = "异常内容")
    private String exceptionMsg;
    
}