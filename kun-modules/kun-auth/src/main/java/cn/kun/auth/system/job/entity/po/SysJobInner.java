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
 * 内部部门职位表
 * </p>
 *
 * @author 廖航
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_job_inner")
@Schema(name = "SysJobInner", description = "内部部门职位表")
public class SysJobInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "内部公司id（关联sys_company_inner表中的id）")
    @TableField("company_inner_id")
    private Long companyInnerId;

    @Schema(description = "内部部门id（关联sys_dept_inner表中的id）")
    @TableField("dept_inner_id")
    private Long deptInnerId;

    @Schema(description = "职位名称")
    @TableField("name")
    private String name;
}
