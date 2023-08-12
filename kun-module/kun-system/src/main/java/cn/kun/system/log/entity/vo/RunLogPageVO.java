package cn.kun.system.log.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 运行日志-分页-返回值
 *
 * @author SkySailStar
 */
@Schema(description = "运行日志-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class RunLogPageVO extends BasePageVO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "流水号")
    private String serialNumber;

    @Schema(description = "方法名")
    private String methodName;

    @Schema(description = "日志级别")
    private String logLevel;

    @Schema(description = "日志级别名称")
    private String logLevelName;

    @Schema(description = "运行时间")
    private LocalDateTime runTime;

    @Schema(description = "数据来源")
    private String dataSource;

    @Schema(description = "数据来源名称")
    private String dataSourceName;

    @Schema(description = "异常编码")
    private String exceptionCode;

    @Schema(description = "异常消息")
    private String exceptionMsg;

}