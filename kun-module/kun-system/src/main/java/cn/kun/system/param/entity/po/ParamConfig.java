package cn.kun.system.param.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 参数配置
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-12 16:16
 */
@Getter
@Setter
@TableName("param_config")
@Schema(name = "ParamConfig", description = "参数配置")
public class ParamConfig extends BaseEntity {

    @Schema(description = "服务ID;关联software_info表id")
    @TableField("service_id")
    private Long serviceId;

    @Schema(description = "编码")
    @TableField("code")
    private String code;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "值")
    @TableField("value")
    private String value;

    @Schema(description = "类型")
    @TableField("type")
    private String type;

    @Schema(description = "预置标识;FLAG")
    @TableField("preset_flag")
    private String presetFlag;
}
