package cn.kun.system.external.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 外部链接-添加-传入值
 *
 * @author SkySailStar
 * @date 2023-04-11 17:40
 */
@Schema(description = "外部链接-添加-传入值")
@Data
public class ExternalUrlAddDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "所属对象id")
    @NotNull(message = "所属对象id：不能为空")
    private Long ownerId;

    @Schema(description = "所属对象类型")
    @NotBlank(message = "所属对象类型：不能为空")
    @Size(max = 64, message = "所属对象类型：长度不能大于64")
    private String ownerType;

    @Schema(description = "logo图片文件id")
    @NotNull(message = "logo图片文件id：不能为空")
    private Long logoId;

    @Schema(description = "名称")
    @NotBlank(message = "名称：不能为空")
    @Size(max = 64, message = "名称：长度不能大于64")
    private String name;

    @Schema(description = "链接地址")
    @NotBlank(message = "链接地址：不能为空")
    @Size(max = 255, message = "链接地址：长度不能大于64")
    private String url;

    @Schema(description = "登录用户名")
    @Size(max = 64, message = "登录用户名：长度不能大于64")
    private String account;

    @Schema(description = "登录密码")
    @Size(max = 64, message = "登录密码：长度不能大于64")
    private String password;

    @Schema(description = "启用标识")
    @Size(max = 1, message = "启用标识：长度不能大于1")
    private String enableFlag;
    
}
