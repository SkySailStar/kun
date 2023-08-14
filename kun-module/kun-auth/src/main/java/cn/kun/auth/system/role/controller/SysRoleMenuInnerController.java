package cn.kun.auth.system.role.controller;

import cn.kun.auth.system.role.service.SysRoleMenuInnerService;
import cn.kun.auth.system.role.entity.dto.RoleMenuDTO;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.kun.base.core.global.controller.BaseController;

import jakarta.validation.Valid;

/**
 * <p>
 * 内部角色菜单表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "内部角色菜单授权")
@RestController
@RequestMapping("/system/sysRoleMenuInner")
public class SysRoleMenuInnerController extends BaseController {
    @Resource
    private SysRoleMenuInnerService sysRoleMenuInnerService;

    /**
     * 角色菜单授权
     * @param dto
     * @return
     */
    @Operation(summary = "角色菜单授权")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> save(@RequestBody @Valid RoleMenuDTO dto) {
        sysRoleMenuInnerService.save(dto);
        return success();
    }


}
