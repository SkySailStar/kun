package cn.kun.system.software.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 软件服务信息-添加-传入值
 *
 * @author SkySailStar
 * @date 2023-04-07 16:37
 */
@Schema(description = "软件服务信息-添加-传入值")
@Data
public class SoftwareInfoAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "服务器ID")
    @NotNull(message = "服务器ID：不能为空")
    private Long serverId;

    @Schema(description = "编码")
    @NotBlank(message = "编码：不能为空")
    @Size(max = 64, message = "编码：长度不能大于64")
    private String code;

    @Schema(description = "名称")
    @NotBlank(message = "名称：不能为空")
    @Size(max = 64, message = "名称：长度不能大于64")
    private String name;

    @Schema(description = "服务类型")
    @NotBlank(message = "服务类型：不能为空")
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
