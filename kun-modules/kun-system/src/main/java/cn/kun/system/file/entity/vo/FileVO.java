package cn.kun.system.file.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 桶内项-返回值
 *
 * @author SkySailStar
 * @date 2023-01-13 11:24
 */
@Schema(description = "桶内项-返回值")
@Data
public class FileVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "大小（单位：bit）")
    private Long size;

    @Schema(description = "所属人")
    private String owner;

    @Schema(description = "更新时间")
    private LocalDateTime updateDate;

    @Schema(description = "是否文件夹")
    private Boolean dir;
}
