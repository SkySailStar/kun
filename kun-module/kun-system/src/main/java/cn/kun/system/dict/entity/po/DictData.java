package cn.kun.system.dict.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 字典数据
 * </p>
 *
 * @author 天航星
 * @since 2023-03-28 17:41
 */
@Getter
@Setter
@TableName("dict_data")
@Schema(name = "DictData", description = "字典数据")
public class DictData extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "类型;关联sys_dict_type表code")
    @TableField("type_code")
    private String typeCode;

    @Schema(description = "键值")
    @TableField("value")
    private String value;

    @Schema(description = "标签")
    @TableField("label")
    private String label;

    @Schema(description = "英文标签")
    @TableField("en_label")
    private String enLabel;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "是否启用;FLAG")
    @TableField("enable_flag")
    private String enableFlag;
}
