package cn.kun.demo.crud.entity.dto;

import cn.kun.base.core.global.entity.dto.BaseEditDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.io.Serial;

/**
 * 字典类型-修改-传入值
 *
 * @author 廖航
 * @date 2023-03-23 10:56
 */
@Schema(description = "字典类型-修改-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypeEditDTO extends BaseEditDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "编码")
    @Size(max = 64, message = "编码：长度不能大于64")
    private String code;

    @Schema(description = "名称")
    @Size(max = 100, message = "名称：长度不能大于100")
    private String name;

    @Schema(description = "英文名称")
    @Size(max = 200, message = "英文名称：长度不能大于200")
    private String enName;
}
