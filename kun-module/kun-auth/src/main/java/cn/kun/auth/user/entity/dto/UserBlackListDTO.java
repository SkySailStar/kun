package cn.kun.auth.user.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;


/**
 * 用户黑名单传入值
 *
 * @author 胡鹤龄
 * @date 2023-07-03 16:03
 */
@Schema(description = "用户黑名单-传入值")
@Data
public class UserBlackListDTO  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    @Schema(description = "状态， 字典：{normal：正常，blacklist：黑名单，cancellation：注销}")
    @NotBlank(message = "状态不能为空")
    @Size(max = 64,message = "状态超出长度")
    private String status;
}
