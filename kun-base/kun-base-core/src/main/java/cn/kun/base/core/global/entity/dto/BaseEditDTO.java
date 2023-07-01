package cn.kun.base.core.global.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 公用-修改-传入值
 *
 * @author 廖航
 * @date 2023-04-11 18:02
 */
@Schema(description = "公用-修改-传入值")
@Data
public class BaseEditDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @NotNull(message = "主键：不能为空")
    private Long id;
    
}
