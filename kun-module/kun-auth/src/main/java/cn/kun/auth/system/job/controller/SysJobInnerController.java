package cn.kun.auth.system.job.controller;

import cn.kun.auth.system.job.entity.dto.JobAddDTO;
import cn.kun.auth.system.job.entity.dto.JobEditDTO;
import cn.kun.auth.system.job.entity.dto.JobPageDTO;
import cn.kun.auth.system.job.entity.po.SysJobInner;
import cn.kun.auth.system.job.entity.vo.JobDetailVO;
import cn.kun.auth.system.job.entity.vo.JobMenuPermissVO;
import cn.kun.auth.system.job.entity.vo.JobPageVO;
import cn.kun.auth.system.job.service.SysJobInnerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.system.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>
 * 内部部门职位表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "内部职位")
@RestController
@RequestMapping("/system/sysJobInner")
public class SysJobInnerController extends BaseController {

    @Resource
    private SysJobInnerService sysJobInnerService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('auth:job:view')")
    public BaseResult<Page<JobPageVO>> page(@RequestBody JobPageDTO dto) {
        return success(sysJobInnerService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:job:view')")
    public BaseResult<JobDetailVO> detail(@PathVariable Long id) {
        return BaseResult.success(sysJobInnerService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('auth:job:add')")
    public BaseResult<SysJobInner> add(@RequestBody @Valid JobAddDTO dto) {
        return success(sysJobInnerService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('auth:job:edit')")
    public BaseResult<SysJobInner> edit(@RequestBody @Valid JobEditDTO dto) {
        return success(sysJobInnerService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:job:remove')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        sysJobInnerService.remove(id);
        return success();
    }

    @Operation(summary = "添加用户-选择职位")
    @GetMapping("jobs/{deptId}")
    @PreAuthorize("@custom.hasAuthority('auth:job:add')")
    public BaseResult jobs(@PathVariable Long deptId) {
        return BaseResult.success(sysJobInnerService.jobs(deptId));
    }

    /**
     * 查询职位的菜单权限
     * @return
     */
    @PostMapping(value = "menuList/job")
    @Operation(summary = "查询职位的菜单权限")
    @PreAuthorize("@custom.hasAuthority('auth:job:view')")
    public BaseResult<JobMenuPermissVO> selectMenuListByJobId(@RequestBody UserMenuPermissDTO dto) {
        return success(sysJobInnerService.selectMenuListByJobId(dto));
    }

    /**
     * 查看职位树状图
     * @return
     */
    @Operation(summary = "查看职位树状图")
    @PreAuthorize("@custom.hasAuthority('auth:job:view')")
    @GetMapping("tree")
    public BaseResult<List<BaseSelectVO>> tree(){
        return success(sysJobInnerService.tree());
    }
}
