package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字符串-键不存在才设值-传入值
 *
 * @author 廖航
 * @date 2023-03-10 15:07
 */
@Schema(description = "字符串-键不存在才设值-传入值")
@Data
public class StringSetIfAbsentDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "值")
    private String value;
}
