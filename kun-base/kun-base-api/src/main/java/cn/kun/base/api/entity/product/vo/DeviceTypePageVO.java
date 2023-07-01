package cn.kun.base.api.entity.product.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 挂载装置类型-分页-返回值
 *
 * @author 廖航
 * @date 2023-03-09 19:03
 */
@Schema(description = "挂载装置类型-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceTypePageVO extends BasePageVO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "编码")
    private String code;
    
    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序")
    private Integer sort;
}