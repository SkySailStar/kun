package cn.kun.demo.crud.entity.vo;

import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 字典类型-分页-返回值
 *
 * @author 廖航
 * @date 2023-03-23 11:30
 */
@Schema(description = "字典类型-分页-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypePageVO extends BasePageVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "英文名称")
    private String enName;
}
