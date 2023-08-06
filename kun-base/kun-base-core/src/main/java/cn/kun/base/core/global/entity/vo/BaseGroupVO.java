package cn.kun.base.core.global.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 公用-分组-返回值
 *
 * @author SkySailStar
 */
@Schema(description = "公用-分组-返回值")
@Data
public class BaseGroupVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 项
     */
    private String item;

    /**
     * 值
     */
    private String value;
    
}