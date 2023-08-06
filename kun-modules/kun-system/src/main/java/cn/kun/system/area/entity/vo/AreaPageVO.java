package cn.kun.system.area.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 区域-分页-返回值
 *
 * @author SkySailStar
 */
@Schema(description = "区域-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class AreaPageVO extends BasePageVO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;
    
    @Schema(description = "名称")
    private String name;

    @Schema(description = "类别")
    private String type;

    @Schema(description = "类别名称")
    private String typeName;

    @Schema(description = "邮政编码")
    private String postalCode;
}