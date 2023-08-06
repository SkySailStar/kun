package cn.kun.system.server.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 服务器信息-添加-传入值
 *
 * @author SkySailStar
 * @date 2023-04-07 10:42
 */
@Schema(description = "服务器信息-添加-传入值")
@Data
public class ServerInfoAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    @NotBlank(message = "编码：不能为空")
    @Size(max = 64, message = "编码：长度不能大于64")
    private String code;

    @Schema(description = "名称")
    @NotBlank(message = "名称：不能为空")
    @Size(max = 255, message = "名称：长度不能大于255")
    private String name;

    @Schema(description = "内网ip")
    @NotBlank(message = "内网ip：不能为空")
    @Size(max = 64, message = "内网ip：长度不能大于64")
    private String intranetIp;

    @Schema(description = "公网ip")
    @Size(max = 64, message = "公网ip：长度不能大于64")
    private String publicIp;

    @Schema(description = "运行状态")
    @Size(max = 64, message = "运行状态：长度不能大于64")
    private String runStatus;

    @Schema(description = "使用状态")
    @Size(max = 64, message = "使用状态：长度不能大于64")
    private String useStatus;

    @Schema(description = "预警标识")
    @Size(max = 1, message = "预警标识：长度不能大于1")
    private String warnFlag;
}
