package cn.kun.base.api.entity.product.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 挂载装置信息-简单-返回值
 *
 * @author SkySailStar
 * @date 2023-03-17 11:19
 */
@Schema(description = "挂载装置信息-简单-返回值")
@Data
public class DeviceInfoSimpleVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "挂载装置唯一编号")
    private String deviceNo;
    
    @Schema(description = "挂载装置名称")
    private String name;

    @Schema(description = "挂载装置类型ID")
    private Long deviceTypeId;

    @Schema(description = "挂载装置定义ID")
    private Long deviceDefineId;

    @Schema(description = "生效状态")
    private String status;
    
}
