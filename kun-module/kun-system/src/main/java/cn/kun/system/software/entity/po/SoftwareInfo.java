package cn.kun.system.software.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 软件服务信息
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-07 10:37
 */
@Getter
@Setter
@TableName("software_info")
@Schema(name = "SoftwareInfo", description = "软件服务信息")
public class SoftwareInfo extends BaseEntity {

    @Schema(description = "服务器id")
    @TableField("server_id")
    private Long serverId;

    @Schema(description = "编码")
    @TableField("code")
    private String code;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "服务类型;SERVER_TYPE")
    @TableField("type")
    private String type;

    @Schema(description = "服务版本号")
    @TableField("version")
    private String version;

    @Schema(description = "内网端口")
    @TableField("intranet_port")
    private Integer intranetPort;

    @Schema(description = "外网端口")
    @TableField("public_port")
    private Integer publicPort;

    @Schema(description = "使用状态;USE_STATUS")
    @TableField("use_status")
    private String useStatus;

    @Schema(description = "运行状态;RUN_STATUS")
    @TableField("run_status")
    private String runStatus;

    @Schema(description = "预警标识;FLAG")
    @TableField("warn_flag")
    private String warnFlag;
}
