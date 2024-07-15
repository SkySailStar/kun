package cn.kun.base.core.global.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公用-分页-返回值
 *
 * @author 天航星
 * @date 2023-03-22 10:19
 */
@Schema(description = "公用-分页-返回值")
@Data
public class BasePageVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;
    
    @Schema(description = "更新人")
    protected String updateName;

    @Schema(description = "更新日期")
    protected LocalDateTime updateDate;
}