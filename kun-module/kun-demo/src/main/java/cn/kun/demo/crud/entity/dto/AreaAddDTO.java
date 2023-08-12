package cn.kun.demo.crud.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 区域-添加-传入值
 *
 * @author SkySailStar
 */
@Schema(description = "区域-添加-传入值")
@Data
public class AreaAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    @NotBlank(message = "编码：不能为空")
    @Size(max = 64, message = "编码：长度不能大于64")
    private String code;

    @Schema(description = "名称")
    @NotBlank(message = "名称：不能为空")
    @Size(max = 64, message = "名称：长度不能大于64")
    private String name;

    @Schema(description = "类别")
    @NotBlank(message = "类别：不能为空")
    @Size(max = 64, message = "类别：长度不能大于64")
    private String type;

    @Schema(description = "邮政编码")
    @Size(max = 10, message = "邮政编码：长度不能大于10")
    private String postalCode;

    @Schema(description = "平面区域")
    private String planeArea;

    @Schema(description = "排序")
    @Max(value = 999, message = "排序：最大值为999")
    private Integer sort;

    @Schema(description = "上级ID")
    private Long parentId;
}
