package cn.kun.system.dict.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 字典类型-分页-传入值
 *
 * @author SkySailStar
 * @date 2023-03-23 11:28
 */
@Schema(description = "字典类型-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypePageDTO extends BasePageDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "英文名称")
    private String enName;
}
