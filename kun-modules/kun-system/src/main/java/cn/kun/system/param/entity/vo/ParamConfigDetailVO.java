package cn.kun.system.param.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 参数配置-详情-返回值
 *
 * @author SkySailStar
 * @date 2023-04-12 16:37
 */
@Schema(description = "参数配置-详情-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ParamConfigDetailVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "服务名称")
    private String serviceName;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "值")
    private String value;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "预置标识名称")
    private String presetFlagName;
}
