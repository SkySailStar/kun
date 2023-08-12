package cn.kun.system.fault.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 故障信息
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:24
 */
@Getter
@Setter
@TableName("fault_info")
@Schema(name = "FaultInfo", description = "故障信息")
public class FaultInfo extends BaseEntity {

    @Schema(description = "编码")
    @TableField("code")
    private String code;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "信息")
    @TableField("info")
    private String info;

    @Schema(description = "项目编号;关联kun_auth.sys_project表project_no")
    @TableField("project_no")
    private String projectNo;

    @Schema(description = "故障来源分类;SOURCE_TYPE")
    @TableField("source_type")
    private String sourceType;

    @Schema(description = "数据来源ID（硬件服务器id、软件服务id、机器人产品id、固定设备产品id）")
    @TableField("source_id")
    private Long sourceId;

    @Schema(description = "状态;FAULT_SOURCE_TYPE")
    @TableField("status")
    private String status;

    @Schema(description = "数据记录时间")
    @TableField("record_time")
    private LocalDateTime recordTime;
}
