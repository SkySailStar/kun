package cn.kun.auth.system.role.controller;

import cn.kun.base.core.global.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 内部角色模板表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@RestController
@RequestMapping("/system/sysRoleTemplateInner")
public class SysRoleTemplateInnerController extends BaseController {
//    @Resource
//    private SysRoleTemplateInnerService sysRoleTemplateInnerService;
//
//    @Operation(summary = "分页列表")
//    @GetMapping
//    @PreAuthorize("@custom.hasAuthority('demo')")
//    public BaseResult<IPage<RoleTemplatePageVO>> page(@RequestBody RoleTemplatePageDTO dto) {
//        return BaseResult.success(sysRoleTemplateInnerService.page(dto));
//    }
//
//    @Operation(summary = "详情")
//    @GetMapping("{id}")
//    @PreAuthorize("@custom.hasAuthority('demo')")
//    public BaseResult<RoleTemplateDetailVO> detail(@PathVariable String id) {
//        return BaseResult.success(sysRoleTemplateInnerService.detail(id));
//    }
//
//    @Operation(summary = "添加")
//    @PostMapping
//    @PreAuthorize("@custom.hasAuthority('demo')")
//    public BaseResult<SysRoleTemplateInner> add(@RequestBody RoleTemplateAddDTO dto) {
//        return BaseResult.success(sysRoleTemplateInnerService.add(dto));
//    }
//
//    @Operation(summary = "修改")
//    @PutMapping("{id}")
//    @PreAuthorize("@custom.hasAuthority('demo')")
//    public BaseResult<SysRoleTemplateInner> edit(@PathVariable String id, @RequestBody RoleTemplateEditDTO dto) {
//        return BaseResult.success(sysRoleTemplateInnerService.edit(id, dto));
//    }
//
//    @Operation(summary = "删除")
//    @DeleteMapping("{id}")
//    @PreAuthorize("@custom.hasAuthority('demo')")
//    public BaseResult<Void> remove(@PathVariable String id) {
//        sysRoleTemplateInnerService.removeById(id);
//        return BaseResult.success();
//    }
}
