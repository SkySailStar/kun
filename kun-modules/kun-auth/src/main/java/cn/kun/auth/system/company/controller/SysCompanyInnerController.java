package cn.kun.auth.system.company.controller;


import cn.kun.auth.system.company.entity.dto.CompanyAddDTO;
import cn.kun.auth.system.company.entity.dto.CompanyEditDTO;
import cn.kun.auth.system.company.entity.dto.CompanyPageDTO;
import cn.kun.auth.system.company.entity.po.SysCompanyInner;
import cn.kun.auth.system.company.entity.vo.CompanyDetailVO;
import cn.kun.auth.system.company.entity.vo.CompanyPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.system.company.service.SysCompanyInnerService;
import cn.kun.base.api.entity.auth.vo.SysCompanyInfoVO;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 内部公司表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "内部公司")
@RestController
@RequestMapping("/system/sysCompanyInner")
public class SysCompanyInnerController extends BaseController {

    @Autowired
    private SysCompanyInnerService sysCompanyInnerService;


    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('auth:company:view')")
    public BaseResult<Page<CompanyPageVO>> page(@RequestBody CompanyPageDTO dto) {
        return success(sysCompanyInnerService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:company:view')")
    public BaseResult<CompanyDetailVO> detail(@PathVariable Long id) {
        return success(sysCompanyInnerService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('auth:company:add')")
    public BaseResult<SysCompanyInner> add(@RequestBody @Valid CompanyAddDTO dto) {
        return success(sysCompanyInnerService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('auth:company:edit')")
    public BaseResult<SysCompanyInner> edit(@RequestBody @Valid CompanyEditDTO dto) {
        return success(sysCompanyInnerService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:company:remove')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        sysCompanyInnerService.remove(id);
        return success();
    }

    /**
     * 通过内部公司id获取内部公司缓存信息
     * @param companyId
     * @return
     */
    @Operation(summary = "获取内部公司缓存信息")
    @GetMapping(value = "getCompanyCacheInfoByCompanyId/{companyId}")
    @PreAuthorize("@custom.hasAuthority('auth:company:view')")
    public BaseResult<SysCompanyInfoVO> getCompanyCacheInfoByCompanyId(@PathVariable Long companyId){
        return success(sysCompanyInnerService.getCompanyCacheInfoByCompanyId(companyId));
    }

    /**
     *  查看内部上级公司信息
     * @return
     */
    @Operation(summary = "查看内部上级公司信息")
    @GetMapping(value = "selectInnerParent")
    @PreAuthorize("@custom.hasAuthority('auth:company:view')")
    public BaseResult selectInnerParent(){
        return success(sysCompanyInnerService.selectInnerParent());
    }
}
