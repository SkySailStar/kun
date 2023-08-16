package cn.kun.auth.user.controller;

import cn.kun.auth.user.entity.dto.UserAddDTO;
import cn.kun.auth.user.entity.dto.UserEditDTO;
import cn.kun.auth.user.entity.dto.UserPageDTO;
import cn.kun.auth.user.entity.dto.UserPasswordDTO;
import cn.kun.auth.user.entity.dto.UserPermissDTO;
import cn.kun.auth.user.entity.po.SysUserInner;
import cn.kun.auth.user.entity.vo.UserMenuPermissVO;
import cn.kun.auth.user.entity.vo.UserPageVO;
import cn.kun.auth.user.entity.vo.UserProjectPermissVO;
import cn.kun.auth.user.entity.vo.UserRolePermissVO;
import cn.kun.auth.user.service.SysUserInnerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.base.api.entity.auth.dto.UserListDTO;
import cn.kun.base.api.entity.auth.vo.SysUserCacheInfoVO;
import cn.kun.base.api.entity.auth.vo.UserDetailVO;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
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
 * 内部用户表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "内部用户")
@RestController
@RequestMapping("/system/sysUserInner")
public class SysUserInnerController extends BaseController {
    @Resource
    private SysUserInnerService sysUserInnerService;

    @Operation(summary = "分页列表")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('auth:user:view')")
    public BaseResult<Page<UserPageVO>> page(@RequestBody UserPageDTO dto) {
        return success(sysUserInnerService.page(dto));
    }

    @Operation(summary = "内部服务调用-根据用户id集合获取用户信息")
    @PostMapping("list")
    @PreAuthorize("@custom.hasAuthority('auth:user:view')")
    public BaseResult<List<UserDetailVO>> list(@RequestBody UserListDTO dto) {
        return success(sysUserInnerService.list(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:user:view')")
    public BaseResult<UserDetailVO> detail(@PathVariable String id) {
        return success(sysUserInnerService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('auth:user:add')")
    public BaseResult<SysUserInner> add(@RequestBody UserAddDTO dto) {
        return success(sysUserInnerService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping()
    @PreAuthorize("@custom.hasAuthority('auth:user:edit')")
    public BaseResult<SysUserInner> edit(@RequestBody @Valid UserEditDTO dto) {
        return success(sysUserInnerService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:user:remove')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        sysUserInnerService.remove(id);
        return success();
    }

    /**
     * 通过内部用户id查询用户缓存信息
     * @param userId
     * @return
     */
    @Operation(summary = "查询用户缓存信息")
    @PreAuthorize("@custom.hasAuthority('auth:user:view')")
    @GetMapping(value = "getUserCacheInfoByUserId/{userId}")
    public BaseResult<SysUserCacheInfoVO> getUserCacheInfoByUserId(@PathVariable Long userId){
        return success(sysUserInnerService.getUserCacheInfoByUserId(userId));
    }

//    /**
//     *  人员配置：获取当前用户所拥有的所有公司信息、部门信息、职位信息的用户
//     * @param projectNo  登录名
//     * @return
//     */
//    @GetMapping(value = "personnel/detail/{projectNo}")
//    @Operation(summary = "人员配置")
//    @PreAuthorize("@custom.hasAuthority('auth:project_personnel:view')")
//    public BaseResult<UserDetailListVO> personnelDetail(@PathVariable String projectNo){
//        return success(sysUserInnerService.personnelDetail(projectNo));
//    }

    /**
     * 用户授权项目、菜单、角色
     * @param dto   用户授权传入值
     * @return
     */
    @PostMapping(value = "savePermiss")
    @Operation(summary = "保存用户权限")
    @PreAuthorize("@custom.hasAuthority('auth:user_permiss:add')")
    public BaseResult<Boolean> savePermiss(@RequestBody UserPermissDTO dto){
        return success(sysUserInnerService.savePermiss(dto));
    }

    /**
     * 根据用户id查询用户菜单授权列表信息
     * @param dto
     * @return
     */
    @PostMapping(value = "menuList/user")
    @Operation(summary = "获取用户所拥有的菜单")
    @PreAuthorize("@custom.hasAuthority('auth:user_menu_permiss:view')")
    public BaseResult<UserMenuPermissVO> selectMenuListByUserId(@RequestBody UserMenuPermissDTO dto) {
        return success(sysUserInnerService.selectMenuListByUserId(dto));
    }

    /**
     * 查询用户-角色权限
     * @param userId
     * @return
     */
    @GetMapping(value = "roleList/{userId}")
    @Operation(summary = "查询用户-角色权限")
    @PreAuthorize("@custom.hasAuthority('auth:user_role_permiss:view')")
    public BaseResult<UserRolePermissVO> selectRoleListByUserId(@PathVariable Long userId){
        return success(sysUserInnerService.selectRoleListByUserId(userId));
    }

    /**
     * 查询用户-项目授权信息
     * @param userId
     * @return
     */
    @Operation(summary = "查询用户-项目授权信息")
    @PreAuthorize("@custom.hasAuthority('auth:user_project_permiss:view')")
    @GetMapping(value = "projectList/{userId}")
    public BaseResult<UserProjectPermissVO> selectProjectListByUserId(@PathVariable Long userId) {
        return success(sysUserInnerService.selectProjectListByUserId(userId));
    }

    /**
     *  修改用户密码
     * @param dto
     * @return
     */
    @Operation(summary = "修改用户密码")
    @PreAuthorize("@custom.hasAuthority('auth:password:edit')")
    @PostMapping(value = "updateUserPassword")
    public BaseResult<Boolean> updateUserPassword(@RequestBody UserPasswordDTO dto) {
        return success(sysUserInnerService.updateUserPassword(dto));
    }
}
