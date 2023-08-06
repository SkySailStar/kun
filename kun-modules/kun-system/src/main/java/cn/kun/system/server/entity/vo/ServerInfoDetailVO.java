package cn.kun.system.server.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 服务器信息-详情-返回值
 *
 * @author SkySailStar
 * @date 2023-04-07 14:01
 */
@Schema(description = "服务器信息-详情-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerInfoDetailVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "内网ip")
    private String intranetIp;

    @Schema(description = "公网ip")
    private String publicIp;

    @Schema(description = "运行状态名称")
    private String runStatusName;

    @Schema(description = "使用状态名称")
    private String useStatusName;

    @Schema(description = "预警标识名称")
    private String warnFlagName;

    @Schema(description = "服务数量")
    private Long serviceNum;
}
