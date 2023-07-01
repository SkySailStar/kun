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
 * 内部职位菜单表
 * </p>
 *
 * @author 廖航
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_job_menu_inner")
@Schema(name = "SysJobMenuInner", description = "内部职位菜单表")
public class SysJobMenuInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "内部角色编号（关联sys_job_inner表中的id）")
    @TableField("job_inner_id")
    private Long jobInnerId;

    @Schema(description = "菜单编号")
    @TableField("menu_id")
    private Long menuId;
}
