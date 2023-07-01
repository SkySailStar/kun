package cn.kun.auth.system.job.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 外部职位菜单表
 * </p>
 *
 * @author 廖航
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_job_menu_outer")
@Schema(name = "SysJobMenuOuter", description = "外部职位菜单表")
public class SysJobMenuOuter extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "外部角色编号（关联sys_job_outer表中的id）")
    @TableField("job_outer_id")
    private Long jobOuterId;

    @Schema(description = "菜单编号")
    @TableField("menu_id")
    private Long menuId;
}
