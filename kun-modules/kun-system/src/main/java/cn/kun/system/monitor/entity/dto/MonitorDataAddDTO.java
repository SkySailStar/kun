package cn.kun.system.monitor.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 监控数据-添加-传入值
 *
 * @author SkySailStar
 * @date 2023-04-11 17:56
 */
@Schema(description = "监控数据-添加-传入值")
@Data
public class MonitorDataAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    @NotBlank(message = "编码：不能为空")
    @Size(max = 100, message = "编码：长度不能大于100")
    private String code;

    @Schema(description = "名称")
    @NotBlank(message = "名称：不能为空")
    @Size(max = 100, message = "名称：长度不能大于100")
    private String name;

    @Schema(description = "值")
    @NotBlank(message = "值：不能为空")
    @Size(max = 100, message = "值：长度不能大于100")
    private String value;

    @Schema(description = "单位")
    @Size(max = 10, message = "单位：长度不能大于10")
    private String unit;

    @Schema(description = "项目编号")
    @Size(max = 64, message = "项目编号：长度不能大于64")
    private String projectNo;

    @Schema(description = "数据来源ID（硬件服务器id、软件服务id、机器人产品id、固定设备产品id）")
    @NotNull(message = "数据来源ID：不能为空")
    private Long sourceId;

    @Schema(description = "监控项来源分类")
    @Size(max = 64, message = "监控项来源分类：长度不能大于64")
    private String sourceType;

    @Schema(description = "详情页展示标识")
    @Size(max = 1, message = "详情页展示标识：长度不能大于1")
    private String detailFlag;

    @Schema(description = "数据记录时间")
    @NotNull(message = "数据记录时间：不能为空")
    private LocalDateTime recordTime;
    
}
