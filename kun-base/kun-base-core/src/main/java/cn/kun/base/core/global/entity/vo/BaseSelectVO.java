package cn.kun.base.core.global.entity.vo;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 公用-下拉框-返回值
 *
 * @author 天航星
 * @date 2023-01-30 15:19
 */
@Schema(description = "公用-下拉框-返回值")
@Data
public class BaseSelectVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键值")
    private String value;
    
    @Schema(description = "标签")
    private String label;

    @Schema(description = "上级键值")
    private String parentValue;

    @Schema(description = "所有上级键值")
    private String parentValues;
    
    @Schema(description = "上级标签")
    private String parentLabel;
    
    @Schema(description = "类型")
    private String type;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "拓展字段对象（包含多个值）")
    private JSONObject expand;
    
    @Schema(description = "子类列表")
    private List<?> children;
}