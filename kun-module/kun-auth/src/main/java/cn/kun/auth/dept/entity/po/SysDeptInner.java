package cn.kun.auth.dept.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 内部公司部门表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_dept_inner")
@Schema(name = "SysDeptInner", description = "内部公司部门表")
public class SysDeptInner extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "上级部门")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "所有上级部门")
    @TableField("parent_ids")
    private String parentIds;

    @Schema(description = "内部公司id（关联sys_company_inner表中的id）")
    @TableField("company_inner_id")
    private Long companyInnerId;

    @Schema(description = "部门名称")
    @TableField("name")
    private String name;
}
