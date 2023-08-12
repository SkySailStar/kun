package cn.kun.demo.crud.entity.po;

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
 * @author SkySailStar
 * @since 2023-02-28 17:12
 */
@Getter
@Setter
@TableName("sys_job_menu_inner")
@Schema(name = "SysJobMenuInner", description = "内部职位菜单表")
public class SysJobMenuInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单编号")
    @TableField("menu_id")
    private Long menuId;

    @Schema(description = "职位编号")
    @TableField("job_inner_id")
    private Long jobInnerId;
}
