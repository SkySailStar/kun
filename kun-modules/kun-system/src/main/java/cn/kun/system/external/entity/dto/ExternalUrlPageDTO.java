package cn.kun.system.external.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 外部链接-分页-传入值
 *
 * @author SkySailStar
 * @date 2023-04-11 17:38
 */
@Schema(description = "外部链接-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalUrlPageDTO extends BasePageDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "所属对象类型")
    private String ownerType;
    
    @Schema(description = "名称")
    private String name;
    
    @Schema(description = "启用标识")
    private String enableFlag;
    
}
