package cn.kun.base.api.entity.product.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 挂载装置定义-分页-返回值
 *
 * @author 廖航
 * @date 2023-03-16 14:47
 */
@Schema(description = "挂载装置定义-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceDefineQueryVO extends BasePageVO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "挂载装置定义名称")
    private String name;

    @Schema(description = "挂载装置类型")
    private String deviceTypeName;

    @Schema(description = "供应商")
    private String supplierName;
}
