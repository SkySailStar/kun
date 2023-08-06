package cn.kun.demo.cache.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通过索引取值-传入值
 *
 * @author SkySailStar
 * @date 2023-01-07 20:46
 */
@Schema(description = "通过索引取值-传入值")
@Data
public class ListGetByIndexDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "键")
    private String key;

    @Schema(description = "索引（索引index>=0时，0：表头，1：第二个元素，依次类推；索引index<0时，-1：表尾，-2：倒数第二个元素，依次类推）")
    private Long index;

}
