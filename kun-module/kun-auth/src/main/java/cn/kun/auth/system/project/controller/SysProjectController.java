package cn.kun.auth.system.project.controller;

import cn.kun.auth.system.project.entity.dto.DeletePersonnelDTO;
import cn.kun.auth.system.project.entity.dto.ProjectAddDTO;
import cn.kun.auth.system.project.entity.dto.ProjectEditDTO;
import cn.kun.auth.system.project.entity.dto.ProjectOaSaveDTO;
import cn.kun.auth.system.project.entity.dto.ProjectPageDTO;
import cn.kun.auth.system.project.entity.dto.SavePersonnelDTO;
import cn.kun.auth.system.project.entity.po.SysProject;
import cn.kun.auth.system.project.entity.vo.PersonnelDetailVO;
import cn.kun.auth.system.project.entity.vo.ProjectPageVO;
import cn.kun.auth.system.project.entity.vo.ProjectRedisVO;
import cn.kun.auth.system.user.entity.dto.UserPersonnelDTO;
import cn.kun.auth.system.user.entity.vo.UserPersonnelPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.system.project.service.SysProjectService;
import cn.kun.base.api.entity.auth.dto.ProjectNoListDTO;
import cn.kun.base.api.entity.auth.dto.UserDTO;
import cn.kun.base.api.entity.auth.vo.ProjectDetailVO;
import cn.kun.base.api.entity.auth.vo.ProjectParentVO;
import cn.kun.base.api.entity.auth.vo.SysProjectInfoVO;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "项目")
@RestController
@RequestMapping("/system/sysProject")
public class SysProjectController extends BaseController {

    @Autowired
    private SysProjectService sysProjectService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('auth:project:view')")
    public BaseResult<Page<ProjectPageVO>> page(@RequestBody ProjectPageDTO dto) {
        return success(sysProjectService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{projectNo}")
    @PreAuthorize("@custom.hasAuthority('auth:project:view')")
    public BaseResult<ProjectDetailVO> detail(@PathVariable String projectNo) {
        return success(sysProjectService.detail(projectNo));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('auth:project:add')")
    public BaseResult<SysProject> add(@RequestBody @Valid ProjectAddDTO dto) {
        return success(sysProjectService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('auth:project:edit')")
    public BaseResult<SysProject> edit(@RequestBody @Valid ProjectEditDTO dto) {
        return success(sysProjectService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:project:remove')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        sysProjectService.remove(id);
        return success();
    }

    /**
     * 通过项目id获取项目信息
     *
     * @param projectNo 项目编号
     * @return
     */
    @Operation(summary = "获取项目信息缓存信息")
    @PreAuthorize("@custom.hasAuthority('auth:project:view')")
    @GetMapping(value = "getProjectInfoByProjectNo/{projectNo}")
    public BaseResult<SysProjectInfoVO> getProjectInfoByProjectNo(@PathVariable String projectNo) {
        return success(sysProjectService.getProjectInfoByProjectNo(projectNo));
    }

    /**
     * 通过内部角色id查询项目权限
     * @param roleInnerId
     * @return
     */
    @Operation(summary = "通过内部角色id查询项目权限")
    @PreAuthorize("@custom.hasAuthority('auth:project:view')")
    @GetMapping(value = "getRoleProjectInnerAuth/{roleInnerId}")
    public BaseResult<List<ProjectRedisVO>> getRoleProjectInnerAuth(@PathVariable Long roleInnerId) {
        return success(sysProjectService.getRoleProjectInnerAuth(roleInnerId));
    }

    /**
     * 保存项目OA
     * @param dto
     * @return
     */
    @Operation(summary = "保存项目OA")
    @PostMapping("save")
    @PreAuthorize("@custom.hasAuthority('auth:project:add')")
    public BaseResult<SysProject> save(@RequestBody @Valid ProjectOaSaveDTO dto) {
        return success(sysProjectService.save(dto));
    }


    @Operation(summary = "人员配置-分页-内部人员")
    @PostMapping("personnelInner/page")
    @PreAuthorize("@custom.hasAuthority('auth:project_personnel:view')")
    public BaseResult<Page<UserPersonnelPageVO>> personnelPage(@RequestBody @Valid UserPersonnelDTO dto) {
        return success(sysProjectService.personnelPage(dto));
    }

    @Operation(summary = "人员配置-获取人员信息树状图")
    @PostMapping("personnel/{projectNo}")
    @PreAuthorize("@custom.hasAuthority('auth:project_personnel:view')")
    public BaseResult<PersonnelDetailVO> personnel(@PathVariable String projectNo) {
        return success(sysProjectService.personnel(projectNo));
    }

    @Operation(summary = "人员配置-分页-外部人员")
    @PostMapping("personnelOuter/page")
    @PreAuthorize("@custom.hasAuthority('auth:project_personnel:view')")
    public BaseResult<Page<UserPersonnelPageVO>> personnelOuterPage(@RequestBody @Valid UserPersonnelDTO dto) {
        return success(sysProjectService.personnelOuterPage(dto));
    }

    @Operation(summary = "人员配置-保存人员-项目")
    @PostMapping("personnel/save")
    @PreAuthorize("@custom.hasAuthority('auth:project_personnel:add')")
    public BaseResult personnelSave(@RequestBody @Valid SavePersonnelDTO dto) {
        return success(sysProjectService.personnelSave(dto));
    }

    @Operation(summary = "人员配置-内部-删除")
    @PostMapping("personnelInner/delete")
    @PreAuthorize("@custom.hasAuthority('auth:project_personnel:remove')")
    public BaseResult personnelDelete(@RequestBody @Valid DeletePersonnelDTO dto) {
        return success(sysProjectService.personnelInnerDelete(dto));
    }

    @Operation(summary = "人员配置-外部-删除")
    @PostMapping("personnelOuter/delete")
    @PreAuthorize("@custom.hasAuthority('auth:project_personnel:remove')")
    public BaseResult personnelOuterDelete(@RequestBody @Valid DeletePersonnelDTO dto) {
        return success(sysProjectService.personnelOuterDelete(dto));
    }

    @Operation(summary = "添加项目-获取当前登陆用户所有项目信息")
    @GetMapping("parentList")
    @PreAuthorize("@custom.hasAuthority('auth:project:view')")
    public BaseResult parentList() {
        return success(sysProjectService.parentList());
    }


    @Operation(summary = "获取用户所有项目信息")
    @PostMapping("projectInfo")
    @PreAuthorize("@custom.hasAuthority('auth:project:view')")
    public BaseResult<List<ProjectParentVO>> projectInfo(@RequestBody @Valid UserDTO dto) {
        return success(sysProjectService.projectInfo(dto));
    }

    /**
     * 通过项目编号列表查询项目信息
     * @param dto
     * @return
     */
    @Operation(summary = "批量查询项目信息")
    @PostMapping("simple/queryListByProjectNos")
    @PreAuthorize("@custom.hasAuthority('auth:project:view')")
    public BaseResult<List<SysProjectInfoVO>> queryListByProjectNos(@RequestBody @Valid ProjectNoListDTO dto) {
        return success(sysProjectService.queryListByProjectNos(dto));
    }
}
