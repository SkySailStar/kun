package cn.kun.base.api.entity.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 字典数据-列表-传入值
 *
 * @author 天航星
 * @date 2023-03-24 17:07
 */
@Schema(description = "字典数据-列表-传入值")
@Data
public class DictDataListDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "类型列表")
    @NotEmpty(message = "类型列表不能为空")
    private List<String> typeList;
}
