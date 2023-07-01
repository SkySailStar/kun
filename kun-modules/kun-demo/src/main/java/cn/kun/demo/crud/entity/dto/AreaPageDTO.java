package cn.kun.demo.crud.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 区域-分页-传入值
 *
 * @author 廖航
 */
@Schema(description = "区域-分页-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class AreaPageDTO extends BasePageDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "行政区域名称")
    private String name;

    @Schema(description = "行政区域类别")
    private String type;

    @Schema(description = "邮政编码")
    private String postalCode;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;
    
    @Schema(description = "排序-排序标识;true正序，false倒序")
    private Boolean sortAsc;

}