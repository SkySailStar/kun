package cn.kun.demo.crud.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 字典类型
 * </p>
 *
 * @author SkySailStar
 * @since 2023-03-28 17:41
 */
@Getter
@Setter
@TableName("dict_type")
@Schema(name = "DictType", description = "字典类型")
public class DictType extends BaseEntity {

    @Schema(description = "编码")
    @TableField("code")
    private String code;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "英文名称")
    @TableField("en_name")
    private String enName;
}
