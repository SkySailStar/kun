package cn.kun.auth.system.user.controller;

import cn.kun.auth.system.user.entity.dto.RoleUserDTO;
import cn.kun.auth.system.user.entity.dto.UserRoleDTO;
import cn.kun.auth.system.user.service.SysUserRoleOuterService;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.kun.base.core.global.controller.BaseController;

import javax.validation.Valid;

/**
 * <p>
 * 外部用户角色表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "外部用户角色")
@RestController
@RequestMapping("/system/sysUserRoleOuter")
public class SysUserRoleOuterController extends BaseController {
    @Autowired
    private SysUserRoleOuterService sysUserRoleOuterService;


    /**
     * 保存数据
     * @param dto
     * @return
     */
    @Operation(summary = "保存")
    @PostMapping("userRole")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> save(@RequestBody @Valid UserRoleDTO dto) {
        sysUserRoleOuterService.save(dto);
        return success();
    }


    /**
     * 保存数据
     * @param dto
     * @return
     */
    @Operation(summary = "保存")
    @PostMapping("roleUser")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> save(@RequestBody @Valid RoleUserDTO dto) {
        sysUserRoleOuterService.save(dto);
        return success();
    }

}
