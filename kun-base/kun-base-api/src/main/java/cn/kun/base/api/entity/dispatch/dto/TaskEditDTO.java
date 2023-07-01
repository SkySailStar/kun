package cn.kun.base.api.entity.dispatch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 任务-修改-传入值
 *
 * @author 廖航
 * @date 2023-06-01 17:11
 */
@Schema(description = "任务-传入值")
@Data
public class TaskEditDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Integer id;
    
    @Schema(description = "执行器ID")
    private Integer executorId;

    @Schema(description = "任务描述")
    private String taskDesc;

    @Schema(description = "负责人")
    private String author;

    @Schema(description = "报警邮件")
    private String alarmEmail;

    @Schema(description = "调度类型（NONE、CRON、FIX_RATE）")
    private String scheduleType;

    @Schema(description = "调度配置，值含义取决于调度类型")
    private String scheduleConf;

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
