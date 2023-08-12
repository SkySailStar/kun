package cn.kun.system.fault.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 故障信息-添加-传入值
 *
 * @author SkySailStar
 * @date 2023-04-11 18:18
 */
@Schema(description = "故障信息-添加-传入值")
@Data
public class FaultInfoAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    @NotBlank(message = "编码：不能为空")
    @Size(max = 100, message = "编码：长度不能大于100")
    private String code;

    @Schema(description = "名称")
    @NotBlank(message = "名称：不能为空")
    @Size(max = 200, message = "名称：长度不能大于200")
    private String name;

    @Schema(description = "信息")
    @Size(max = 1024, message = "名称：长度不能大于1024")
    private String info;

    @Schema(description = "项目编号")
    @Size(max = 64, message = "项目编号：长度不能大于64")
    private String projectNo;

    @Schema(description = "故障来源分类")
    @Size(max = 64, message = "故障来源分类：长度不能大于64")
    private String sourceType;

    @Schema(description = "数据来源ID（硬件服务器id、软件服务id、机器人产品id、固定设备产品id）")
    @NotNull(message = "数据来源ID：不能为空")
    private Long sourceId;

    @Schema(description = "状态")
    @Size(max = 64, message = "状态：长度不能大于64")
    private String status;

    @Schema(description = "数据记录时间")
    @NotNull(message = "数据记录时间：不能为空")
    private LocalDateTime recordTime;
}
