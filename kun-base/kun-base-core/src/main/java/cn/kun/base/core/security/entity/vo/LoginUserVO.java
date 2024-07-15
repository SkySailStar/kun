package cn.kun.base.core.security.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 登录用户-返回值
 *
 * @author 天航星
 * @date 2023-03-24 14:20
 */
@Schema(description = "登录用户-返回值")
@Data
public class LoginUserVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long id;
    
    @Schema(description = "用户名称")
    private String name;
    
    @Schema(description = "用户头像")
    private String avatar;
}
