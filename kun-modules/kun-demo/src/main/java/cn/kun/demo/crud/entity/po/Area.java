package cn.kun.demo.crud.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 行政区划
 * </p>
 *
 * @author 廖航
 * @since 2023-04-06 18:08
 */
@Getter
@Setter
@TableName("area")
@Schema(name = "Area", description = "行政区划")
public class Area extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "上级id")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "所有上级id")
    @TableField("parent_ids")
    private String parentIds;

    @Schema(description = "编码")
    @TableField("code")
    private String code;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "类别;AREA_TYPE")
    @TableField("type")
    private String type;

    @Schema(description = "邮政编码")
    @TableField("postal_code")
    private String postalCode;

    @Schema(description = "平面区域")
    @TableField("plane_area")
    private String planeArea;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;
}
