package cn.kun.system.error.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 错误信息
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:22
 */
@Getter
@Setter
@TableName("error_info")
@Schema(name = "ErrorInfo", description = "错误信息")
public class ErrorInfo extends BaseEntity {

    @Schema(description = "编码")
    @TableField("code")
    private String code;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "英文名称")
    @TableField("en_name")
    private String enName;

    @Schema(description = "扩展信息")
    @TableField("extend_info")
    private String extendInfo;

    @Schema(description = "类型;ERROR_TYPE")
    @TableField("type")
    private String type;
}
