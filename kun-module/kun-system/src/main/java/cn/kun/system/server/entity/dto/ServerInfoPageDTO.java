package cn.kun.system.server.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 服务器信息-分页-传入值
 *
 * @author SkySailStar
 * @date 2023-04-07 13:55
 */
@Schema(description = "服务器信息-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerInfoPageDTO extends BasePageDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;
    
    @Schema(description = "名称")
    private String name;

    @Schema(description = "运行状态;RUN_STATUS")
    private String runStatus;

    @Schema(description = "使用状态;USE_STATUS")
    private String useStatus;

    @Schema(description = "预警标识;FLAG")
    private String warnFlag;
}
