package cn.kun.system.param.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 参数配置-分页-传入值
 *
 * @author SkySailStar
 * @date 2023-04-12 16:18
 */
@Schema(description = "参数配置-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ParamConfigPageDTO extends BasePageDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "服务ID")
    private Long serviceId;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "预置标识")
    private String presetFlag;
}
