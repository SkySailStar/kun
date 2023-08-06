package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字符串缓存递增递减-传入值
 *
 * @author SkySailStar
 * @date 2023-01-07 20:38
 */
@Schema(description = "字符串缓存递增递减-传入值")
@Data
public class StringIncDecDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "要增加或者减少几")
    private Long num;
}
