package cn.kun.system.external.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 外部链接-分页-返回值
 *
 * @author SkySailStar
 * @date 2023-04-11 17:45
 */
@Schema(description = "外部链接-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalUrlPageVO extends BasePageVO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "所属对象类型;OWNER_TYPE")
    private String ownerType;

    @Schema(description = "所属对象类型名称")
    private String ownerTypeName;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "链接地址")
    private String url;

    @Schema(description = "启用标识")
    private String enableFlag;

    @Schema(description = "启用标识名称")
    private String enableFlagName;
}
