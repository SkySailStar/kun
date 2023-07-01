package cn.kun.base.api.entity.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 文件信息-返回值
 *
 * @author 廖航
 * @date 2023-04-28 11:21
 */
@Data
public class FileInfoVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "类型名称")
    private String typeName;

    @Schema(description = "参数")
    private String param;
    
}
