package cn.kun.auth.user.entity.po;

import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serial;

/**
 * 用户
 *
 * @author SkySailStar
 * @date 2024-04-24 10:08
 */
@Schema(description = "用户")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "账号")
    @TableField("user_name")
    private String userName;
    
    @Schema(description = "姓名")
    @TableField("nick_name")
    private String nickName;
    
    @Schema(description = "部门ID")
    @TableField("dept_id")
    private String deptId;
    
    @Schema(description = "联系电话")
    @TableField("phone")
    private String phone;
    
    @Schema(description = "邮箱")
    @TableField("email")
    private String email;
    
    @Schema(description = "头像")
    @TableField("avatar")
    private String avatar;
    
}
