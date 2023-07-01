package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author eric
 * @date 2023/3/6 11:38
 */
@Schema(description = "部门缓存信息-返回值")
@Data
public class DeptInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "部门id")
    private Long deptId;

    @Schema(description = "上级id")
    private String parentId;

    @Schema(description = "所有上级id")
    private String parentIds;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "公司id")
    private Long companyId;

    @Schema(description = "职位信息")
    private List<JobInfoVO> jobList;

}