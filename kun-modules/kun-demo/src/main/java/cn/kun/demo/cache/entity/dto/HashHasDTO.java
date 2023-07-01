package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 哈希缓存判断键和项是否存在-传入值
 *
 * @author 廖航
 * @date 2023-01-09 09:32
 */
@Schema(description = "哈希缓存判断键和项是否存在-传入值")
@Data
public class HashHasDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "项")
    private String item;
}