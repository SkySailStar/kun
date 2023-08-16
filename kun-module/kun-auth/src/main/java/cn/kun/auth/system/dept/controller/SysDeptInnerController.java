package cn.kun.auth.system.dept.controller;

import cn.kun.auth.system.dept.entity.dto.DeptAddDTO;
import cn.kun.auth.system.dept.entity.dto.DeptEditDTO;
import cn.kun.auth.system.dept.entity.dto.DeptPageDTO;
import cn.kun.auth.system.dept.entity.po.SysDeptInner;
import cn.kun.auth.system.dept.entity.vo.DeptDetailVO;
import cn.kun.auth.system.dept.entity.vo.DeptPageVO;
import cn.kun.auth.system.dept.service.SysDeptInnerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * 内部公司部门表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "内部部门")
@RestController
@RequestMapping("/system/sysDeptInner")
public class SysDeptInnerController extends BaseController {

    @Resource
    private SysDeptInnerService sysDeptInnerService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('auth:dept:view')")
    public BaseResult<Page<DeptPageVO>> page(@RequestBody DeptPageDTO dto) {
        return success(sysDeptInnerService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:dept:view')")
    public BaseResult<DeptDetailVO> detail(@PathVariable Long id) {
        return BaseResult.success(sysDeptInnerService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('auth:dept:add')")
    public BaseResult<SysDeptInner> add(@RequestBody @Valid DeptAddDTO dto) {
        return success(sysDeptInnerService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('auth:dept:edit')")
    public BaseResult<SysDeptInner> edit(@RequestBody @Valid DeptEditDTO dto) {
        return success(sysDeptInnerService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:dept:remove')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        sysDeptInnerService.remove(id);
        return success();
    }

    /**
     * 查看内部上级部门信息
     * @return
     */
    @Operation(summary = "查看内部上级部门信息")
    @PreAuthorize("@custom.hasAuthority('auth:dept:view')")
    @GetMapping("selectInnerParent/{companyId}")
    public BaseResult selectInnerParent(@PathVariable Long companyId){
        return success(sysDeptInnerService.selectInnerParent(companyId));
    }

    /**
     * 查看部门树状图
     * @return
     */
    @Operation(summary = "查看部门树状图")
    @PreAuthorize("@custom.hasAuthority('auth:dept:view')")
    @GetMapping("tree")
    public BaseResult<List<BaseSelectVO>> tree(){
        return success(sysDeptInnerService.tree());
    }

}
