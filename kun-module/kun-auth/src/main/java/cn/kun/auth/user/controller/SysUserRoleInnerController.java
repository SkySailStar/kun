package cn.kun.auth.user.controller;

import com.sevnce.auth.user.entity.dto.RoleUserDTO;
import com.sevnce.auth.user.entity.dto.UserRoleDTO;
import com.sevnce.auth.user.service.SysUserRoleInnerService;
import com.sevnce.base.core.global.controller.BaseController;
import com.sevnce.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 * <p>
 * 内部用户角色表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "内部用户角色")
@RestController
@RequestMapping("/system/sysUserRoleInner")
public class SysUserRoleInnerController extends BaseController {

    @Autowired
    private SysUserRoleInnerService sysUserRoleInnerService;


    /**
     * 保存数据
     * @param dto
     * @return
     */
    @Operation(summary = "保存")
    @PostMapping("userRole")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> save(@RequestBody @Valid UserRoleDTO dto) {
        sysUserRoleInnerService.save(dto);
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
        sysUserRoleInnerService.save(dto);
        return success();
    }

}
