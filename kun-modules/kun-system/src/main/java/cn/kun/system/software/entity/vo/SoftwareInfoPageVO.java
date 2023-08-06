package cn.kun.system.software.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 软件服务信息-分页-返回值
 *
 * @author SkySailStar
 * @date 2023-04-07 17:30
 */
@Schema(description = "软件服务信息-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class SoftwareInfoPageVO extends BasePageVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "服务器ID")
    private Long serverId;
    
    @Schema(description = "服务器名称")
    private String serverName;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "服务类型")
    private String type;

    @Schema(description = "服务类型名称")
    private String typeName;

    @Schema(description = "服务版本号")
    private String version;

    @Schema(description = "内网端口")
    private Integer intranetPort;

    @Schema(description = "外网端口")
    private Integer publicPort;

    @Schema(description = "使用状态")
    private String useStatus;

    @Schema(description = "使用状态名称")
    private String useStatusName;

    @Schema(description = "运行状态")
    private String runStatus;

    @Schema(description = "运行状态名称")
    private String runStatusName;

    @Schema(description = "预警标识")
    private String warnFlag;

    @Schema(description = "预警标识名称")
    private String warnFlagName;
}
