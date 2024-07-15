package cn.kun.system.dict.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 字典类型-添加-传入值
 *
 * @author 天航星
 * @date 2023-03-23 10:56
 */
@Schema(description = "字典类型-添加-传入值")
@Data
public class DictTypeAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    @NotBlank(message = "编码：不能为空")
    @Size(max = 64, message = "编码：长度不能大于64")
    private String code;

    @Schema(description = "名称")
    @NotBlank(message = "名称：不能为空")
    @Size(max = 100, message = "名称：长度不能大于100")
    private String name;

    @Schema(description = "英文名称")
    @Size(max = 200, message = "英文名称：长度不能大于200")
    private String enName;
}
