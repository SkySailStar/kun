package cn.kun.auth.system.dept.controller;

import cn.kun.auth.system.dept.entity.dto.DeptAddDTO;
import cn.kun.auth.system.dept.entity.dto.DeptEditDTO;
import cn.kun.auth.system.dept.entity.dto.DeptPageDTO;
import cn.kun.auth.system.dept.entity.po.SysDeptOuter;
import cn.kun.auth.system.dept.entity.vo.DeptDetailVO;
import cn.kun.auth.system.dept.entity.vo.DeptPageVO;
import cn.kun.auth.system.dept.entity.vo.SysDeptInfoVO;
import cn.kun.auth.system.dept.service.SysDeptOuterService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 外部公司部门表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "外部部门")
@RestController
@RequestMapping("/system/sysDeptOuter")
public class SysDeptOuterController extends BaseController {
    @Autowired
    private SysDeptOuterService sysDeptOuterService;


    /**
     * 保存外部公司部门数据
     * @param dto
     * @return
     */
    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('auth:dept:view')")
    public BaseResult<Page<DeptPageVO>> page(@RequestBody DeptPageDTO dto) {
        return success(sysDeptOuterService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:dept:view')")
    public BaseResult<DeptDetailVO> detail(@PathVariable Long id) {
        return BaseResult.success(sysDeptOuterService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('auth:dept:add')")
    public BaseResult<SysDeptOuter> add(@RequestBody @Valid DeptAddDTO dto) {
        return success(sysDeptOuterService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('auth:dept:edit')")
    public BaseResult<SysDeptOuter> edit(@RequestBody @Valid DeptEditDTO dto) {
        return success(sysDeptOuterService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:dept:remove')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        sysDeptOuterService.remove(id);
        return success();
    }

    /**
     * 通过部门id查询部门缓存信息
     * @param deptId
     * @return
     */
    @Operation(summary = "查询部门缓存信息-暂不使用")
    @PreAuthorize("@custom.hasAuthority('auth:dept:view')")
    @GetMapping("getDeptCacheInfoByDeptId/{deptId}")
    public BaseResult<SysDeptInfoVO> getDeptCacheInfoByDeptId(@PathVariable String deptId){
        return success(sysDeptOuterService.getDeptCacheInfoByDeptId(deptId));
    }

    /**
     * 查看内部上级部门信息
     * @return
     */
    @Operation(summary = "查看外部上级部门信息")
    @PreAuthorize("@custom.hasAuthority('auth:dept:view')")
    @GetMapping("selectOuterParent/{companyId}")
    public BaseResult selectOuterParent(@PathVariable Long companyId){
        return success(sysDeptOuterService.selectOuterParent(companyId));
    }

    /**
     * 查看部门树状图
     * @return
     */
    @Operation(summary = "查看部门树状图")
    @PreAuthorize("@custom.hasAuthority('auth:dept:view')")
    @GetMapping("tree")
    public BaseResult<List<BaseSelectVO>> tree(){
        return success(sysDeptOuterService.tree());
    }
}
