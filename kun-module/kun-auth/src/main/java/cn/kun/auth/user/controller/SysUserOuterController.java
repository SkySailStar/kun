package cn.kun.auth.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sevnce.auth.menu.entity.dto.UserMenuPermissDTO;
import com.sevnce.auth.user.entity.dto.UserAddDTO;
import com.sevnce.auth.user.entity.dto.UserBlackListDTO;
import com.sevnce.auth.user.entity.dto.UserEditDTO;
import com.sevnce.auth.user.entity.dto.UserPageDTO;
import com.sevnce.auth.user.entity.dto.UserPasswordDTO;
import com.sevnce.auth.user.entity.dto.UserPermissDTO;
import com.sevnce.auth.user.entity.po.SysUserOuter;
import com.sevnce.auth.user.entity.vo.UserMenuPermissVO;
import com.sevnce.auth.user.entity.vo.UserPageVO;
import com.sevnce.auth.user.entity.vo.UserProjectPermissVO;
import com.sevnce.auth.user.entity.vo.UserRolePermissVO;
import com.sevnce.auth.user.service.SysUserOuterService;
import com.sevnce.base.api.entity.auth.dto.UserListDTO;
import com.sevnce.base.api.entity.auth.vo.SysUserCacheInfoVO;
import com.sevnce.base.api.entity.auth.vo.UserDetailVO;
import com.sevnce.base.core.global.controller.BaseController;
import com.sevnce.base.core.global.entity.BaseResult;
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
import java.util.List;

/**
 * <p>
 * 外部用户表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "外部用户")
@RestController
@RequestMapping("/system/sysUserOuter")
public class SysUserOuterController extends BaseController {
    @Autowired
    private SysUserOuterService sysUserOuterService;


    @Operation(summary = "分页列表")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('auth:user_outer:view')")
    /**
     * 1、如果需要验证同时具有多个权限，则用 AND 拼接：
     *    @PreAuthorize("@custom.hasAuthority('demo') AND hasAuthority('demo1')")
     * 2、如果只需要验证任一权限，则用 OR 拼接：
     *    @PreAuthorize("@custom.hasAuthority('demo') OR hasAuthority('demo1')")
     *    或者用 hasAnyAuthority：
     *    @PreAuthorize("hasAnyAuthority('demo', 'demo1')")
     * 3、如果需要验证自定义权限标识，则用：
     *    @PreAuthorize("@custom.hasAuthority('demo')")
     */
    public BaseResult<Page<UserPageVO>> page(@RequestBody UserPageDTO dto) {
        return success(sysUserOuterService.page(dto));
    }

    @Operation(summary = "内部服务调用-根据用户id集合获取用户信息")
    @PostMapping("list")
    @PreAuthorize("@custom.hasAuthority('auth:user:view')")
    public BaseResult<List<UserDetailVO>> list(@RequestBody UserListDTO dto) {
        return success(sysUserOuterService.list(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:user:view')")
    public BaseResult<UserDetailVO> detail(@PathVariable String id) {
        return success(sysUserOuterService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('auth:user:add')")
    public BaseResult<SysUserOuter> add(@RequestBody UserAddDTO dto) {
        return success(sysUserOuterService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping()
    @PreAuthorize("@custom.hasAuthority('auth:user:edit')")
    public BaseResult<SysUserOuter> edit( @RequestBody @Valid UserEditDTO dto) {
        return success(sysUserOuterService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:user:remove')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        sysUserOuterService.remove(id);
        return success();
    }

    /**
     * 通过用户id查询用户缓存信息
     * @param userId
     * @return
     */
    @GetMapping(value = "getUserCacheInfoByUserId/{userId}")
    @Operation(summary = "查询用户缓存")
    @PreAuthorize("@custom.hasAuthority('auth:user:view')")
    public BaseResult<SysUserCacheInfoVO> getUserCacheInfoByUserId(@PathVariable Long userId){
        return success(sysUserOuterService.getUserCacheInfoByUserId(userId));
    }
//    /**
//     *  人员配置：获取当前外部用户所拥有的所有公司信息、部门信息、职位信息的用户
//     * @param projectNo  登录名
//     * @return
//     */
//    @GetMapping(value = "personnel/detail/{projectNo}")
//    @Operation(summary = "人员配置")
//    @PreAuthorize("@custom.hasAuthority('auth:project_personnel:view')")
//    public BaseResult<UserDetailListVO> personnelDetail(@PathVariable String projectNo){
//        return success(sysUserOuterService.personnelDetail(projectNo));
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
        return success(sysUserOuterService.savePermiss(dto));
    }

    /**
     * 查询用户-菜单权限列表
     * @param dto
     * @return
     */
    @PostMapping(value = "menuList/user")
    @Operation(summary = "查询用户-菜单权限列表")
    @PreAuthorize("@custom.hasAuthority('auth:user_menu_permiss:view')")
    public BaseResult<UserMenuPermissVO> selectMenuListByUserId(@RequestBody UserMenuPermissDTO dto){
        return success(sysUserOuterService.selectMenuListByUserId(dto));
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
        return success(sysUserOuterService.selectProjectListByUserId(userId));
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
        return success(sysUserOuterService.selectRoleListByUserId(userId));
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
        return success(sysUserOuterService.updateUserPassword(dto));
    }

    /**
     * 加入、移除黑名单
     * @param dto
     * @return  返回用户信息
     */
    @Operation(summary = "加入、移除黑名单")
    @PreAuthorize("@custom.hasAuthority('auth:user:edit')")
    @PostMapping(value = "blacklist/edit")
    public BaseResult<Boolean> edit(@RequestBody @Valid UserBlackListDTO dto) {
        return success(sysUserOuterService.edit(dto));
    }

    /**
     * 用户注销
     * @param dto
     * @return
     */
    @Operation(summary = "用户-注销")
    @PreAuthorize("@custom.hasAuthority('auth:user:remove')")
    @PostMapping(value = "log-off")
    public BaseResult<Boolean> logOff(@RequestBody @Valid UserBlackListDTO dto) {
        return success(sysUserOuterService.logOff(dto));
    }
}
