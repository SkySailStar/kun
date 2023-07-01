package cn.kun.base.core.mq.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据处理记录-消息队列-传入值
 *
 * @author 廖航
 * @date 2023-04-26 15:44
 */
@Schema(description = "数据处理记录-消息队列-传入值")
@Data
public class DataProcessRecordMqDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目名称")
    @Size(max = 100, message = "项目名称：长度不能大于100")
    private String projectName;

    @Schema(description = "产品名称")
    @Size(max = 100, message = "产品名称：长度不能大于100")
    private String productName;

    @Schema(description = "业务名称")
    @NotBlank(message = "业务名称：不能为空")
    @Size(max = 100, message = "业务名称：长度不能大于100")
    private String businessName;

    @Schema(description = "接收的数据")
    @NotBlank(message = "接收的数据：不能为空")
    @Size(max = 100, message = "接收的数据：长度不能大于2048")
    private String receiveData;

    @Schema(description = "接收时间")
    @NotNull(message = "接收时间：不能为空")
    private LocalDateTime receiveTime;

    @Schema(description = "处理时间")
    private LocalDateTime processTime;
    
    @Schema(description = "处理结果")
    @Size(max = 255, message = "处理结果：长度不能大于255")
    private String processResult;

    @Schema(description = "处理前文件路径")
    @Size(max = 255, message = "处理前文件路径：长度不能大于255")
    private String processPreFilePath;

    @Schema(description = "处理后文件路径")
    @Size(max = 255, message = "处理后文件路径：长度不能大于255")
    private String processSufFilePath;

    @Schema(description = "推送时间")
    private LocalDateTime pushTime;
    
}
