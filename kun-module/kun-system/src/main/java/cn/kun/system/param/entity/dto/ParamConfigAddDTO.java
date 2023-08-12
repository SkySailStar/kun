package cn.kun.system.param.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 参数配置-添加-传入值
 *
 * @author SkySailStar
 * @date 2023-04-12 16:19
 */
@Schema(description = "参数配置-添加-传入值")
@Data
public class ParamConfigAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "服务ID")
    private Long serviceId;

    @Schema(description = "编码")
    @NotBlank(message = "编码：不能为空")
    @Size(max = 100, message = "编码：长度不能大于100")
    private String code;

    @Schema(description = "名称")
    @NotBlank(message = "名称：不能为空")
    @Size(max = 200, message = "名称：长度不能大于200")
    private String name;

    @Schema(description = "值")
    @NotBlank(message = "值：不能为空")
    @Size(max = 1024, message = "值：长度不能大于1024")
    private String value;

    @Schema(description = "类型")
    @Size(max = 64, message = "类型：长度不能大于64")
    private String type;

    @Schema(description = "预置标识")
    @Size(max = 1, message = "预置标识：长度不能大于1")
    private String presetFlag;
}
