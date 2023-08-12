package cn.kun.system.monitor.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 监控项配置-添加-传入值
 *
 * @author SkySailStar
 * @date 2023-04-10 10:17
 */
@Schema(description = "监控项配置-添加-传入值")
@Data
public class MonitorConfigAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "上级id")
    @NotNull(message = "上级id：不能为空")
    private Long parentId;
    
    @Schema(description = "类别")
    @NotBlank(message = "类别：不能为空")
    @Size(max = 64, message = "类别：长度不能大于64")
    private String category;

    @Schema(description = "类型")
    @NotBlank(message = "类型：不能为空")
    @Size(max = 64, message = "类型：长度不能大于64")
    private String type;

    @Schema(description = "编码")
    @NotBlank(message = "编码：不能为空")
    @Size(max = 64, message = "编码：长度不能大于64")
    private String code;

    @Schema(description = "名称")
    @NotBlank(message = "名称：不能为空")
    @Size(max = 64, message = "名称：长度不能大于64")
    private String name;

    @Schema(description = "展示类型")
    @Size(max = 64, message = "展示类型：长度不能大于64")
    private String showType;

    @Schema(description = "启用标识")
    @Size(max = 1, message = "启用标识：长度不能大于1")
    private String enableFlag;

    @Schema(description = "详情页展示标识")
    @Size(max = 1, message = "详情页展示标识：长度不能大于1")
    private String detailFlag;

    @Schema(description = "排序")
    private Integer sort;
    
}
