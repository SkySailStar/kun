package cn.kun.auth.dept.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Schema(description = "部门缓存信息-返回值")
@Data
public class SysDeptInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "部门id")
    private String deptId;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "职位名称列表")
    private List<String> jobNameList;

    @Schema(description = "菜单权限列表")
    private List<String> menuPermsList;

}
