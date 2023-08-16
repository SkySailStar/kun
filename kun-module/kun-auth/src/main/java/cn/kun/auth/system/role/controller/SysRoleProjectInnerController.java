package cn.kun.auth.system.role.controller;

import cn.kun.auth.system.role.service.SysRoleProjectInnerService;
import cn.kun.auth.system.role.entity.dto.RoleProjectDTO;
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

import jakarta.validation.Valid;

/**
 * <p>
 * 内部角色项目表 前端控制器
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:23
 */
@Tag(name = "内部角色项目授权")
@RestController
@RequestMapping("/system/sysRoleProjectInner")
public class SysRoleProjectInnerController extends BaseController {
    @Autowired
    private SysRoleProjectInnerService sysRoleProjectInnerService;

    /**
     * 内部角色项目授权
     * @param dto
     * @return
     */
    @Operation(summary = "内部角色项目授权")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> save(@RequestBody @Valid RoleProjectDTO dto) {
        sysRoleProjectInnerService.save(dto);
        return success();
    }
}
