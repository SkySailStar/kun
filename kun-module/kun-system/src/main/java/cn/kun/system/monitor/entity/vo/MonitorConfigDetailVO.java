package cn.kun.system.monitor.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 监控项配置-详情-返回值
 *
 * @author SkySailStar
 * @date 2023-04-10 10:24
 */
@Schema(description = "监控项配置-详情-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class MonitorConfigDetailVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "上级名称")
    private String parentName;

    @Schema(description = "类别名称")
    private String categoryName;

    @Schema(description = "类型名称")
    private String typeName;

    @Schema(description = "编码")
    @TableField("code")
    private String code;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "展示类型名称")
    private String showTypeName;

    @Schema(description = "启用标识名称")
    private String enableFlagName;
    
    @Schema(description = "详情页展示标识名称")
    private String detailFlagName;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;
    
}
