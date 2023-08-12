package cn.kun.system.software.entity.dto;

import cn.kun.base.core.global.entity.dto.BaseEditDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.Size;
import java.io.Serial;

/**
 * 软件服务信息-修改-传入值
 *
 * @author SkySailStar
 * @date 2023-04-07 16:37
 */
@Schema(description = "软件服务信息-修改-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class SoftwareInfoEditDTO extends BaseEditDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "服务器ID")
    private Long serverId;

    @Schema(description = "编码")
    @Size(max = 64, message = "编码：长度不能大于64")
    private String code;

    @Schema(description = "名称")
    @Size(max = 64, message = "名称：长度不能大于64")
    private String name;

    @Schema(description = "服务类型")
    @Size(max = 64, message = "服务类型：长度不能大于64")
    private String type;

    @Schema(description = "服务版本号")
    @Size(max = 64, message = "服务版本号：长度不能大于64")
    private String version;

    @Schema(description = "内网端口")
    private Integer intranetPort;

    @Schema(description = "外网端口")
    private Integer publicPort;

    @Schema(description = "使用状态")
    @Size(max = 64, message = "使用状态：长度不能大于64")
    private String useStatus;

    @Schema(description = "运行状态")
    @Size(max = 64, message = "运行状态：长度不能大于64")
    private String runStatus;

    @Schema(description = "预警标识")
    @Size(max = 1, message = "预警标识：长度不能大于1")
    private String warnFlag;
}
