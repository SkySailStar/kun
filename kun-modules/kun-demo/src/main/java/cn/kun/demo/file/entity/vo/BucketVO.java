package cn.kun.demo.file.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 桶信息-返回值
 *
 * @author 廖航
 * @date 2023-01-13 10:06
 */
@Schema(description = "桶信息-返回值")
@Data
public class BucketVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "创建时间")
    private LocalDateTime createDate;
}
