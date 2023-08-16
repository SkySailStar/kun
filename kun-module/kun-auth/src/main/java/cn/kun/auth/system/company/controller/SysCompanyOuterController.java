package cn.kun.auth.system.company.controller;

import cn.kun.auth.system.company.entity.dto.CompanyAddDTO;
import cn.kun.auth.system.company.entity.dto.CompanyEditDTO;
import cn.kun.auth.system.company.entity.dto.CompanyPageDTO;
import cn.kun.auth.system.company.entity.po.SysCompanyOuter;
import cn.kun.auth.system.company.entity.vo.CompanyDetailVO;
import cn.kun.auth.system.company.entity.vo.CompanyPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.system.company.service.SysCompanyOuterService;
import cn.kun.base.api.entity.auth.vo.SysCompanyInfoVO;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * <p>
 * 外部公司表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name="外部公司")
@RestController
@RequestMapping("/system/sysCompanyOuter")
public class SysCompanyOuterController extends BaseController {
    @Autowired
    private SysCompanyOuterService sysCompanyOuterService;


    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('auth:company:view')")
    public BaseResult<Page<CompanyPageVO>> page(@RequestBody CompanyPageDTO dto) {
        return success(sysCompanyOuterService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:company:view')")
    public BaseResult<CompanyDetailVO> detail(@PathVariable Long id) {
        return success(sysCompanyOuterService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('auth:company:add')")
    public BaseResult<SysCompanyOuter> add(@RequestBody @Valid CompanyAddDTO dto) {
        return success(sysCompanyOuterService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('auth:company:edit')")
    public BaseResult<SysCompanyOuter> edit(@RequestBody @Valid CompanyEditDTO dto) {
        return success(sysCompanyOuterService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:company:remove')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        sysCompanyOuterService.remove(id);
        return success();
    }

    /**
     * 通过外部公司id获取外部公司缓存信息
     * @param companyId
     * @return
     */
    @Operation(summary = "获取外部公司缓存信息")
    @GetMapping(value = "getCompanyCacheInfoByCompanyId/{companyId}")
    @PreAuthorize("@custom.hasAuthority('auth:company:view')")
    public BaseResult<SysCompanyInfoVO> getCompanyCacheInfoByCompanyId(@PathVariable Long companyId){
        return success(sysCompanyOuterService.getCompanyCacheInfoByCompanyId(companyId));
    }

    /**
     *  查看外部上级公司信息
     * @param
     * @return
     */
    @Operation(summary = "查看外部上级公司信息")
    @GetMapping(value = "auth:company:view")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult selectOuterParent(){
        return success(sysCompanyOuterService.selectOuterParent());
    }
}
