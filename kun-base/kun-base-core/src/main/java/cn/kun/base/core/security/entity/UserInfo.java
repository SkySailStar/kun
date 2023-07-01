package cn.kun.base.core.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 * @author 廖航
 */
@Schema(description = "用户信息")
@Data
public class UserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private String id;

    @Schema(description = "登录名")
    private String loginName;

    @Schema(description = "密码")
    @JsonIgnore
    private String password;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "状态（0：正常；1注销；2：黑名单）")
    private String status;

    @Schema(description = "所属公司ID")
    private String companyId;
    
    @Schema(description = "项目编码列表")
    private List<String> projectNoList;

    @Schema(description = "内外部标识;true内部,false外部")
    private Boolean characteristic;
}
