package cn.kun.system.error.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 错误信息-分页-返回值
 *
 * @author SkySailStar
 * @date 2023-04-11 17:34
 */
@Schema(description = "错误信息-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorInfoPageVO extends BasePageVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;
    
    @Schema(description = "类型")
    private String type;

    @Schema(description = "类型名称")
    private String typeName;
}
