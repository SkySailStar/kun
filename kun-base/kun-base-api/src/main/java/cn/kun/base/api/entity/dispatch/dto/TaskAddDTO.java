package cn.kun.base.api.entity.dispatch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/**
 * 任务-添加-传入值
 *
 * @author 天航星
 * @date 2023-06-01 17:11
 */
@Schema(description = "任务-添加-传入值")
@Data
public class TaskAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "执行器ID")
    private Integer executorId;

    @Schema(description = "任务描述")
    @NotBlank(message = "任务描述：不能为空")
    private String taskDesc;
    
    @Schema(description = "负责人")
    @NotBlank(message = "负责人：不能为空")
    private String author;

    @Schema(description = "报警邮件")
    private String alarmEmail;

    @Schema(description = "调度类型（NONE、CRON、FIX_RATE）")
    @NotBlank(message = "调度类型：不能为空")
    private String scheduleType;

    @Schema(description = "调度配置，值含义取决于调度类型")
    @NotBlank(message = "调度配置：不能为空")
    private String scheduleConf;

    @Schema(description = "运行模式（BEAN、GLUE_GROOVY、GLUE_SHELL、GLUE_PYTHON、GLUE_PHP、GLUE_NODEJS、GLUE_POWERSHELL）")
    @NotBlank(message = "运行模式：不能为空")
    private String glueType;

    @Schema(description = "GLUE源代码")
    private String glueSource;

    @Schema(description = "GLUE备注")
    private String glueRemark;

    @Schema(description = "执行器，任务Handler名称")
    private String executorHandler;
    
    @Schema(description = "调度过期策略")
    private String misfireStrategy;

    @Schema(description = "执行器路由策略")
    private String executorRouteStrategy;

    @Schema(description = "执行器，任务参数")
    private String executorParam;

    @Schema(description = "阻塞处理策略")
    private String executorBlockStrategy;

    @Schema(description = "任务执行超时时间，单位秒")
    private Integer executorTimeout;

    @Schema(description = "失败重试次数")
    private Integer executorFailRetryCount;

    @Schema(description = "子任务ID，多个逗号分隔")
    private String childJobId;
    
}
