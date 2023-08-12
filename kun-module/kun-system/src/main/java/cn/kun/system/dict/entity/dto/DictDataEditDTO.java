package cn.kun.system.dict.entity.dto;

import cn.kun.base.core.global.entity.dto.BaseEditDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.Size;
import java.io.Serial;

/**
 * 字典数据-修改-传入值
 *
 * @author SkySailStar
 * @date 2023-03-23 14:09
 */
@Schema(description = "字典数据-修改-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class DictDataEditDTO extends BaseEditDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "类型")
    @Size(max = 64, message = "类型：长度不能大于64")
    private String typeCode;

    @Schema(description = "键值")
    @Size(max = 64, message = "键值：长度不能大于64")
    private String value;

    @Schema(description = "标签")
    @Size(max = 100, message = "标签：长度不能大于100")
    private String label;

    @Schema(description = "英文标签")
    @Size(max = 200, message = "英文标签：长度不能大于200")
    private String enLabel;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "启用标识")
    private String enableFlag;
}
