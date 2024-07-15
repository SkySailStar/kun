package cn.kun.system.log.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志-添加-传入值
 *
 * @author 天航星
 */
@Schema(description = "操作日志-添加-传入值")
@Data
public class OperateLogAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单ID")
    @NotNull(message = "菜单ID：不能为空")
    private Long menuId;

    @Schema(description = "流水号")
    @NotNull(message = "流水号：不能为空")
    @Size(max = 64, message = "流水号：长度不能大于64")
    private String serialNumber;

    @Schema(description = "操作类型")
    @NotNull(message = "操作类型：不能为空")
    @Size(max = 64, message = "操作类型：长度不能大于64")
    private String operateType;

    @Schema(description = "操作用户名称")
    @NotNull(message = "操作用户名称：不能为空")
    @Size(max = 64, message = "操作用户名称：长度不能大于64")
    private String operateUser;

    @Schema(description = "操作时间")
    @NotNull(message = "操作时间：不能为空")
    private LocalDateTime operateTime;

    @Schema(description = "操作数据（用户操作时，传入的数据）")
    private String operateData;

    @Schema(description = "操作内容")
    @Size(max = 200, message = "操作内容：长度不能大于200")
    private String operateContent;

    @Schema(description = "操作标识")
    @Size(max = 1, message = "操作标识：长度不能大于1")
    private String operateFlag;

    @Schema(description = "操作对象类型")
    @Size(max = 64, message = "操作对象类型：长度不能大于64")
    private String operateTargetType;

    @Schema(description = "操作对象编码")
    @Size(max = 64, message = "操作对象编码：长度不能大于64")
    private String operateTargetCode;

    @Schema(description = "异常编码")
    @Size(max = 64, message = "异常编码：长度不能大于64")
    private String exceptionCode;

    @Schema(description = "异常内容")
    private String exceptionMsg;
    
}