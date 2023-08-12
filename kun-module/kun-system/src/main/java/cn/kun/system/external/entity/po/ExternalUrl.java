package cn.kun.system.external.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 外部链接
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:23
 */
@Getter
@Setter
@TableName("external_url")
@Schema(name = "ExternalUrl", description = "外部链接")
public class ExternalUrl extends BaseEntity {

    @Schema(description = "所属对象id")
    @TableField("owner_id")
    private Long ownerId;

    @Schema(description = "所属对象类型;EXTERNAL_URL_OWNER_TYPE")
    @TableField("owner_type")
    private String ownerType;

    @Schema(description = "logo图片文件id")
    @TableField("logo_id")
    private Long logoId;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "链接地址")
    @TableField("url")
    private String url;

    @Schema(description = "登录用户名")
    @TableField("account")
    private String account;

    @Schema(description = "登录密码")
    @TableField("password")
    private String password;

    @Schema(description = "启用标识;FLAG")
    @TableField("enable_flag")
    private String enableFlag;
}
