package cn.kun.auth.security.entity.po;

import cn.kun.base.core.global.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 用户
 *
 * @author 天航星
 * @date 2024-07-15 16:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUser extends BaseEntity {
  
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 账号
     */
    @TableField("user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 姓名
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 状态（true：启用；false：停用）
     */
    @TableField("status")
    private Boolean status;
}
