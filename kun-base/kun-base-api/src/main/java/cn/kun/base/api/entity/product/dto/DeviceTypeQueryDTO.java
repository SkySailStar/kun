package cn.kun.base.api.entity.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 挂载装置类型-查询-传入值
 *
 * @author SkySailStar
 * @date 2023-03-09 18:52
 */
@Schema(description = "挂载装置类型-查询-传入值")
@Data
public class DeviceTypeQueryDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "编码")
    private String code;
    
    @Schema(description = "名称")
    private String name;
}