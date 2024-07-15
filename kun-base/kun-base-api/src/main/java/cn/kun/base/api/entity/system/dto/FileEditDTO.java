package cn.kun.base.api.entity.system.dto;

import cn.kun.base.core.global.entity.dto.BaseEditDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 文件修改-传入值
 *
 * @author 天航星
 * @date 2023-04-28 10:37
 */
@Schema(description = "文件修改-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class FileEditDTO extends BaseEditDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "参数")
    private String param;
    
}
