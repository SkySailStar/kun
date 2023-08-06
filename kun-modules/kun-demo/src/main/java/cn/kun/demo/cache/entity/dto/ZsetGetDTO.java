package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 有序集合缓存取值-传入值
 *
 * @author SkySailStar
 * @date 2023-03-13 09:37
 */
@Schema(description = "有序集合缓存取值-传入值")
@Data
public class ZsetGetDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "键")
    private String key;
    
    @Schema(description = "开始位置")
    private Long start;
    
    @Schema(description = "结束位置")
    private Long end;
    
}
