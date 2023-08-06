package cn.kun.system.external.entity.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 外部链接-详情-返回值
 *
 * @author SkySailStar
 * @date 2023-04-11 17:49
 */
@Schema(description = "外部链接-详情-返回值")
@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalUrlDetailVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "所属对象名称")
    private String ownerName;

    @Schema(description = "所属对象类型名称")
    private String ownerTypeName;

    @Schema(description = "logo图片文件路径")
    private String logoPath;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "链接地址")
    private String url;

    @Schema(description = "登录用户名")
    private String account;

    @Schema(description = "登录密码")
    private String password;

    @Schema(description = "启用标识名称")
    private String enableFlagName;
    
}
