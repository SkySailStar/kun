package cn.kun.system.monitor.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 监控数据
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:23
 */
@Getter
@Setter
@TableName("monitor_data")
@Schema(name = "MonitorData", description = "监控数据")
public class MonitorData extends BaseEntity {

    @Schema(description = "编码")
    @TableField("code")
    private String code;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "值")
    @TableField("value")
    private String value;

    @Schema(description = "单位")
    @TableField("unit")
    private String unit;

    @Schema(description = "项目编号;关联kun_auth.sys_project表project_no")
    @TableField("project_no")
    private String projectNo;

    @Schema(description = "数据来源ID（硬件服务器id、软件服务id、机器人产品id、固定设备产品id）")
    @TableField("source_id")
    private Long sourceId;

    @Schema(description = "监控项来源分类;MONITOR_SOURCE_TYPE")
    @TableField("source_type")
    private String sourceType;

    @Schema(description = "详情页展示标识;FLAG")
    @TableField("detail_flag")
    private String detailFlag;

    @Schema(description = "数据记录时间")
    @TableField("record_time")
    private LocalDateTime recordTime;
}
