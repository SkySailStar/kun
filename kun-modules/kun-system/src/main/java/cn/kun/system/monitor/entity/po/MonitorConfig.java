package cn.kun.system.monitor.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 监控项配置
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-10 10:08
 */
@Getter
@Setter
@TableName("monitor_config")
@Schema(name = "MonitorConfig", description = "监控项配置")
public class MonitorConfig extends BaseEntity {

    @Schema(description = "上级id")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "所有上级")
    @TableField("parent_ids")
    private String parentIds;

    @Schema(description = "类别;MONITOR_CATEGORY")
    @TableField("category")
    private String category;

    @Schema(description = "类型;MONITOR_TYPE")
    @TableField("type")
    private String type;

    @Schema(description = "编码")
    @TableField("code")
    private String code;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "展示类型;SHOW_TYPE")
    @TableField("show_type")
    private String showType;

    @Schema(description = "启用标识;FLAG")
    @TableField("enable_flag")
    private String enableFlag;

    @Schema(description = "详情页展示标识;FLAG")
    @TableField("detail_flag")
    private String detailFlag;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;
}
