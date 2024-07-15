package cn.kun.base.api.entity.product.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 挂载装置类型-分页-传入值
 *
 * @author 天航星
 * @date 2023-03-09 18:52
 */
@Schema(description = "挂载装置类型-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceTypePageDTO extends BasePageDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "编码")
    private String code;
    
    @Schema(description = "名称")
    private String name;
}