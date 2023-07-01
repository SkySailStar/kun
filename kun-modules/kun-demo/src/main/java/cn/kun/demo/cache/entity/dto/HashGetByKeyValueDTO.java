package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 哈希缓存根据键和项取值-传入值
 *
 * @author 廖航
 * @date 2023-01-08 21:47
 */
@Schema(description = "哈希缓存根据键和项取值-传入值")
@Data
public class HashGetByKeyValueDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "项")
    private String item;
}
