package cn.kun.system.log.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 运行日志-添加-传入值
 *
 * @author SkySailStar
 */
@Schema(description = "运行日志-添加-传入值")
@Data
public class RunLogAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "流水号")
    @NotNull(message = "流水号：不能为空")
    @Size(max = 64, message = "流水号：长度不能大于64")
    private String serialNumber;

    @Schema(description = "方法名")
    @NotNull(message = "方法名：不能为空")
    @Size(max = 100, message = "方法名：长度不能大于100")
    private String methodName;

    @Schema(description = "日志级别")
    @NotNull(message = "日志级别：不能为空")
    @Size(max = 64, message = "日志级别：长度不能大于64")
    private String logLevel;

    @Schema(description = "运行时间")
    private LocalDateTime runTime;

    @Schema(description = "日志内容")
    private String logContent;

    @Schema(description = "数据来源")
    @NotNull(message = "数据来源：不能为空")
    @Size(max = 64, message = "数据来源：长度不能大于64")
    private String dataSource;

    @Schema(description = "异常编码")
    @Size(max = 50, message = "异常编码：长度不能大于50")
    private String exceptionCode;

    @Schema(description = "异常消息")
    private String exceptionMsg;
    
}