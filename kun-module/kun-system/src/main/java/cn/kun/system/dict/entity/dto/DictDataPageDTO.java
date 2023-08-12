package cn.kun.system.dict.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 字典数据-分页-传入值
 *
 * @author SkySailStar
 * @date 2023-03-23 14:05
 */
@Schema(description = "字典数据-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class DictDataPageDTO extends BasePageDTO {
    
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

    @Schema(description = "启用标识")
    private String enableFlag;
}
