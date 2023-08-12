package cn.kun.base.core.global.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 主键列表-公用传入值
 *
 * @author SkySailStar
 * @date 2023-02-08 11:48
 */
@Schema(description = "主键列表-公用传入值")
@Data
public class BaseIdListDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键列表")
    @NotEmpty(message = "主键列表不能为空")
    private List<Long> idList;
    
}