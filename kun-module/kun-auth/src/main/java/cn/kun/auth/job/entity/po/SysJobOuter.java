package cn.kun.auth.job.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 外部部门职位表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_job_outer")
@Schema(name = "SysJobOuter", description = "外部部门职位表")
public class SysJobOuter extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "外部公司id（关联sys_company_outer表中的id）")
    @TableField("company_outer_id")
    private Long companyOuterId;

    @Schema(description = "外部部门id（关联sys_dept_outer表中的id）")
    @TableField("dept_outer_id")
    private Long deptOuterId;

    @Schema(description = "职位名称")
    @TableField("name")
    private String name;
}
