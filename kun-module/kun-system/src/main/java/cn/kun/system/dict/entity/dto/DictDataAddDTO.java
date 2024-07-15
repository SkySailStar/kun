package cn.kun.system.dict.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 字典数据-添加-传入值
 *
 * @author 天航星
 * @date 2023-03-23 14:09
 */
@Schema(description = "字典数据-添加-传入值")
@Data
public class DictDataAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "类型")
    @NotBlank(message = "类型：不能为空")
    @Size(max = 64, message = "类型：长度不能大于64")
    private String typeCode;

    @Schema(description = "键值")
    @NotBlank(message = "键值：不能为空")
    @Size(max = 32, message = "键值：长度不能大于32")
    private String value;

    @Schema(description = "标签")
    @NotBlank(message = "标签：不能为空")
    @Size(max = 100, message = "标签：长度不能大于100")
    private String label;

    @Schema(description = "英文标签")
    @Size(max = 200, message = "英文标签：长度不能大于200")
    private String enLabel;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "启用标识")
    @Size(max = 1, message = "启用标识：长度不能大于1")
    private String enableFlag;
}
