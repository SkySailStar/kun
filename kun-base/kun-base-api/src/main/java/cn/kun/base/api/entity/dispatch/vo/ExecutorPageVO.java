package cn.kun.base.api.entity.dispatch.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 执行器-分页-返回值
 *
 * @author 天航星
 * @date 2023-06-05 19:02
 */
@Schema(description = "执行器-分页-返回值")
@Data
public class ExecutorPageVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Integer id;
    
    @Schema(description = "应用编码")
    private String appCode;

    @Schema(description = "应用名称")
    private String appName;

    @Schema(description = "执行器地址类型;ADDRESS_TYPE")
    private String addressType;
    
    @Schema(description = "执行器地址类型名称")
    private String addressTypeName;
    
    @Schema(description = "执行器地址列表，多地址逗号分隔")
    private String addressList;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
