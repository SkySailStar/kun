package cn.kun.auth.user.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sevnce.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serial;

/**
 * <p>
 * 外部用户职位表
 * </p>
 *
 * @author 廖航
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_user_job_outer")
@Schema(name = "SysUserJobOuter", description = "外部用户职位表")
public class SysUserJobOuter extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "外部用户id（关联sys_user_outer表中的id）")
    @TableField("user_outer_id")
    private Long userOuterId;

    @Schema(description = "外部职位id（关联sys_job_outer表中的id）")
    @TableField("job_outer_id")
    private Long jobOuterId;
}
