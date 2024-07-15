package cn.kun.base.api.entity.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 挂载装置定义-查询-传入值
 *
 * @author 天航星
 * @date 2023-03-16 14:40
 */
@Schema(description = "挂载装置定义-查询-传入值")
@Data
public class DeviceDefineQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "挂载装置定义名称")
    private String name;

    @Schema(description = "挂载装置类型ID")
    private Long deviceTypeId;

    @Schema(description = "供应商ID")
    private Long supplierId;
}
