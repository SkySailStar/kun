package cn.kun.system.file.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 文件上传-返回值
 *
 * @author 天航星
 * @date 2023-03-28 18:30
 */
@Schema(description = "文件上传-返回值")
@Data
public class UploadVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "文件ID")
    private String fileId;
    
    @Schema(description = "文件路径")
    private String filePath;
}
