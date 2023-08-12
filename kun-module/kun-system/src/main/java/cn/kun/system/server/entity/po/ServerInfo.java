package cn.kun.system.server.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 服务器信息
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-07 10:35
 */
@Getter
@Setter
@TableName("server_info")
@Schema(name = "ServerInfo", description = "服务器信息")
public class ServerInfo extends BaseEntity {

    @Schema(description = "编码")
    @TableField("code")
    private String code;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "内网ip")
    @TableField("intranet_ip")
    private String intranetIp;

    @Schema(description = "公网ip")
    @TableField("public_ip")
    private String publicIp;

    @Schema(description = "运行状态;RUN_STATUS")
    @TableField("run_status")
    private String runStatus;

    @Schema(description = "使用状态;USE_STATUS")
    @TableField("use_status")
    private String useStatus;

    @Schema(description = "预警标识;FLAG")
    @TableField("warn_flag")
    private String warnFlag;
}
