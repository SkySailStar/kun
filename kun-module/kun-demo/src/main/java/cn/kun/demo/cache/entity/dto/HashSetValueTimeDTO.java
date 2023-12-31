package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 哈希缓存设值若不存在则创建并设值生效时间-传入值
 *
 * @author SkySailStar
 * @date 2023-01-09 09:26
 */
@Schema(description = "哈希缓存设值若不存在则创建并设值生效时间-传入值")
@Data
public class HashSetValueTimeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "项")
    private String item;

    @Schema(description = "值")
    private Object value;

    @Schema(description = "生效时间")
    private Long time;
}
