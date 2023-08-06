package cn.kun.system.dict.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 字典数据-详情-返回值
 *
 * @author SkySailStar
 * @date 2023-03-23 14:14
 */
@Schema(description = "字典数据-详情-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class DictDataDetailVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "类型")
    private String typeCode;

    @Schema(description = "键值")
    private String value;

    @Schema(description = "标签")
    private String label;

    @Schema(description = "英文标签")
    private String enLabel;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否启用;1是，0否")
    private String enableFlag;
}
