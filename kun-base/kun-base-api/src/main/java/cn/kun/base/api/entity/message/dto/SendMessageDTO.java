package cn.kun.base.api.entity.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.util.List;

/**
 * @author eric
 * @date 2023/4/19 18:03
 */
@Schema(description = "发送消息-添加-传入值")
@Data
public class SendMessageDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "消息模板ID")
    private Long messageTemplateId;

    @Schema(description = "跳转链接")
    @Size(max = 200, message = "跳转链接：长度不能大于200")
    private String link;

    @Schema(description = "消息标题")
    @NotBlank(message = "消息标题：不能为空")
    @Size(max = 200, message = "消息标题：长度不能大于200")
    private String title;

    @Schema(description = "附件ID")
    private Long fileId;

    @Schema(description = "消息内容")
    @NotBlank(message = "消息内容：不能为空")
    @Size(max = 5000, message = "消息内容：长度不能大于5000")
    private String content;

    @Schema(description = "备注")
    @Size(max = 255, message = "备注：长度不能大于255")
    private String remarks;

    @Schema(description = "消息来源")
    @NotBlank(message = "消息来源：不能为空")
    @Size(max = 32, message = "消息来源：长度不能大于32")
    private String source;

    @Schema(description = "消息类型")
    @NotBlank(message = "消息类型：不能为空")
    @Size(max = 32, message = "消息类型：长度不能大于32")
    private String type;

    @Schema(description = "推送用户id")
    @NotNull(message = "推送用户id：不能为空")
    List<Long> receiverIdList;

}