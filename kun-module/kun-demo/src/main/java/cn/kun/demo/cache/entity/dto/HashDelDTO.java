package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 哈希缓存删除-传入值
 *
 * @author SkySailStar
 * @date 2023-01-09 09:36
 */
@Schema(description = "哈希缓存删除-传入值")
@Data
public class HashDelDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "项")
    private Object item;
}
