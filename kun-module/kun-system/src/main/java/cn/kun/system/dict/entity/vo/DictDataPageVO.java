package cn.kun.system.dict.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 字典数据-分页-返回值
 *
 * @author 天航星
 * @date 2023-03-23 14:12
 */
@Schema(description = "字典数据-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class DictDataPageVO extends BasePageVO {
    
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
