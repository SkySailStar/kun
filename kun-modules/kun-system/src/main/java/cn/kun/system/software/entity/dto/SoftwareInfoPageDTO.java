package cn.kun.system.software.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 软件服务信息-分页-传入值
 *
 * @author SkySailStar
 * @date 2023-04-07 17:28
 */
@Schema(description = "软件服务信息-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class SoftwareInfoPageDTO extends BasePageDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "服务器ID")
    private Long serverId;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "服务类型")
    private String type;

    @Schema(description = "服务版本号")
    private String version;

    @Schema(description = "使用状态")
    private String useStatus;

    @Schema(description = "运行状态")
    private String runStatus;

    @Schema(description = "预警标识")
    private String warnFlag;
}
