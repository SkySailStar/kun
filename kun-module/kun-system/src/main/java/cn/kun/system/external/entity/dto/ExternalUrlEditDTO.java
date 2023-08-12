package cn.kun.system.external.entity.dto;

import cn.kun.base.core.global.entity.dto.BaseEditDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.Size;
import java.io.Serial;

/**
 * 外部链接-修改-传入值
 *
 * @author SkySailStar
 * @date 2023-04-11 17:40
 */
@Schema(description = "外部链接-修改-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalUrlEditDTO extends BaseEditDTO {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "所属对象id")
    private Long ownerId;

    @Schema(description = "所属对象类型")
    @Size(max = 64, message = "所属对象类型：长度不能大于64")
    private String ownerType;

    @Schema(description = "logo图片文件id")
    private Long logoId;

    @Schema(description = "名称")
    @Size(max = 64, message = "名称：长度不能大于64")
    private String name;

    @Schema(description = "链接地址")
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
