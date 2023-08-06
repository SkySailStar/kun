package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 设值-传入值
 *
 * @author SkySailStar
 * @date 2023-01-07 20:11
 */
@Schema(description = "设值-传入值")
@Data
public class StringSetDTO implements Serializable {

    @Schema(description = "键")
    private String key;

    @Schema(description = "值")
    private Object value;

}