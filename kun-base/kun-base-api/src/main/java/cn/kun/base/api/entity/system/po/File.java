package cn.kun.base.api.entity.system.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 文件
 * </p>
 *
 * @author 廖航
 * @since 2023-03-28 19:22
 */
@Getter
@Setter
@TableName("file")
@Schema(name = "File", description = "文件")
public class File extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "类型;FILE_TYPE")
    @TableField("type")
    private String type;

    @Schema(description = "大小（单位：bit）")
    @TableField("size")
    private Long size;

    @Schema(description = "路径")
    @TableField("path")
    private String path;

    @Schema(description = "存储位置;STORAGE_LOCATION")
    @TableField("storage_location")
    private String storageLocation;

    @Schema(description = "缩略图id")
    @TableField("thumbnail_id")
    private Long thumbnailId;

    @Schema(description = "是否临时文件;FLAG")
    @TableField("temp_flag")
    private String tempFlag;

    @Schema(description = "是否加密存储;FLAG")
    @TableField("encrypt_flag")
    private String encryptFlag;

    @Schema(description = "是否压缩存储;FLAG")
    @TableField("compress_flag")
    private String compressFlag;

    @Schema(description = "生效标识;FLAG")
    @TableField("enable_flag")
    private String enableFlag;
    
    @Schema(description = "参数")
    @TableField("param")
    private String param;
}
