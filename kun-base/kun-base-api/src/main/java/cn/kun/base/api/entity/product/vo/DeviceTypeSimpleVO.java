package cn.kun.base.api.entity.product.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 挂载装置类型-简单-返回值
 *
 * @author 廖航
 * @date 2023-05-19 15:54
 */
@Schema(description = "挂载装置类型-简单-返回值")
@Data
public class DeviceTypeSimpleVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序")
    private Integer sort;
}
