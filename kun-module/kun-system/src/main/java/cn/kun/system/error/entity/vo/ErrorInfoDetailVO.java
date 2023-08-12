package cn.kun.system.error.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 错误信息-详情-返回值
 *
 * @author SkySailStar
 * @date 2023-04-11 17:36
 */
@Schema(description = "错误信息-详情-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorInfoDetailVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "英文名称")
    private String enName;

    @Schema(description = "扩展信息")
    private String extendInfo;
    
    @Schema(description = "类型名称")
    private String typeName;
}
