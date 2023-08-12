package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 哈希缓存设值-传入值
 *
 * @author SkySailStar
 * @date 2023-01-08 21:55
 */
@Schema(description = "哈希缓存设值-传入值")
@Data
public class HashSetMapDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "值")
    private Map<String, Object> value;
}
