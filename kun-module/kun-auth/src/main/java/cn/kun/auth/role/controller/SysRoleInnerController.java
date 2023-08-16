package cn.kun.auth.role.controller;

import cn.kun.auth.role.entity.dto.RoleEditDTO;
import cn.kun.auth.role.entity.dto.RolePersonnelDTO;
import cn.kun.auth.role.entity.dto.RoleUsersDTO;
import cn.kun.auth.role.service.SysRoleInnerService;
import cn.kun.auth.role.entity.dto.RoleAddDTO;
import cn.kun.auth.role.entity.dto.RolePageDTO;
import cn.kun.auth.role.entity.dto.RolePermissDTO;
import cn.kun.auth.user.entity.vo.RoleProjectPermissVO;
import cn.kun.auth.user.entity.vo.UserPersonnelPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.auth.role.entity.po.SysRoleInner;
import cn.kun.auth.role.entity.vo.PersonnelRoleVO;
import cn.kun.auth.role.entity.vo.RoleDetailVO;
import cn.kun.auth.role.entity.vo.RoleMenuPermissVO;
import cn.kun.auth.role.entity.vo.RolePageVO;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>
 * 内部角色表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "内部角色")
@RestController
@RequestMapping("/system/sysRoleInner")
public class SysRoleInnerController extends BaseController {
    @Resource
    private SysRoleInnerService sysRoleInnerService;

    /**
     * 分页
     * @param dto 角色分页列表-传入值
     * @return 角色分页列表-返回值
     */
    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('auth:role:view')")
    public BaseResult<Page<RolePageVO>> page(@RequestBody RolePageDTO dto) {
        return success(sysRoleInnerService.page(dto));
    }

    /**
     * 详情
     * @param id 主键
     * @return 角色详情-返回值
     */
    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:role:view')")
    public BaseResult<RoleDetailVO> detail(@PathVariable Long id) {
        return BaseResult.success(sysRoleInnerService.detail(id));
    }

    /**
     * 添加
     * @param dto 角色添加-传入值
     * @return 角色对象-返回值
     */
    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('auth:role:add')")
    public BaseResult<SysRoleInner> add(@RequestBody @Valid RoleAddDTO dto) {
        return success(sysRoleInnerService.add(dto));
    }

    /**
     * 修改
     * @param dto 角色修改-传入值
     * @return 角色对象-返回值
     */
    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('auth:role:edit')")
    public BaseResult<SysRoleInner> edit(@RequestBody @Valid RoleEditDTO dto) {
        return success(sysRoleInnerService.edit(dto));
    }

    /**
     * 根据主键删除
     * @param id 主键
     */
    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:role:remove')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        sysRoleInnerService.remove(id);
        return success();
    }

    /**
     * 通过内部角色id查询角色缓存信息
     * @param roleInnerId
     * @return
     */
    @Operation(summary = "查询角色缓存信息")
    @PreAuthorize("@custom.hasAuthority('auth:role:view')")
    @GetMapping(value = "getRoleInnerInfoByRoleInnerId/{roleInnerId}")
    public BaseResult getRoleInnerInfoByRoleInnerId(@PathVariable Long roleInnerId){
        return success(sysRoleInnerService.getRoleInnerInfoByRoleInnerId(roleInnerId));
    }

    /**
     *  人员配置：获取当前用户所拥有的所有公司信息、部门信息、职位信息的用户
     * @param roleId  角色id
     * @return
     */
    @GetMapping(value = "personnel/role_info/{roleId}")
    @Operation(summary = "人员配置")
    @PreAuthorize("@custom.hasAuthority('auth:role_user_permiss:view') OR hasAuthority('auth:role_personnel:view')")
    public BaseResult<PersonnelRoleVO> personnelRoleInfo(@PathVariable Long roleId){
        return success(sysRoleInnerService.personnelRoleInfo(roleId));
    }

    @Operation(summary = "人员配置-分页")
    @PostMapping("personnel/role_info/page")
    @PreAuthorize("@custom.hasAuthority('auth:role_personnel:view')")
    public BaseResult<Page<UserPersonnelPageVO>> personnelRoleInfoPage(@RequestBody @Valid RolePersonnelDTO dto) {
        return success(sysRoleInnerService.personnelRoleInfoPage(dto));
    }

    @Operation(summary = "人员配置-删除")
    @PostMapping("personnel/role_info/delete")
    @PreAuthorize("@custom.hasAuthority('auth:role_personnel:remove')")
    public BaseResult personnelRoleInfoDelete(@RequestBody @Valid RoleUsersDTO dto) {
        sysRoleInnerService.personnelRoleInfoDelete(dto);
        return success();
    }

    /**
     * 角色授权项目、菜单、用户
     * @param dto 角色授权-传入值
     * @return 角色授权-返回值
     */
    @PostMapping(value = "savePermiss")
    @Operation(summary = "保存角色权限")
    @PreAuthorize("@custom.hasAuthority('auth:role_permiss:add')")
    public BaseResult<Boolean> savePermiss(@RequestBody RolePermissDTO dto){
        return success(sysRoleInnerService.savePermiss(dto));
    }

    @Operation(summary = "人员配置-保存人员")
    @PostMapping("personnel/save")
    @PreAuthorize("@custom.hasAuthority('auth:role_personnel:add')")
    public BaseResult personnelSave(@RequestBody @Valid RolePersonnelDTO dto) {
        return success(sysRoleInnerService.personnelSave(dto));
    }

    /**
     * 根据角色id查询菜单权限信息
     * @param dto
     * @return
     */
    @PostMapping(value = "menuList/role")
    @Operation(summary = "角色菜单权限列表")
    @PreAuthorize("@custom.hasAuthority('auth:role_menu_permiss:view')")
    public BaseResult<RoleMenuPermissVO> selectMenuListByRoleId(@RequestBody UserMenuPermissDTO dto) {
        return success(sysRoleInnerService.selectMenuListByRoleId(dto));
    }

    /**
     * 查询角色-项目权限
     * @param roleId
     * @return
     */
    @Operation(summary = "查询角色的项目权限")
    @PreAuthorize("@custom.hasAuthority('auth:role_project_permiss:view')")
    @GetMapping(value = "projectList/{roleId}")
    public BaseResult<RoleProjectPermissVO> selectProjectListByRoleId(@PathVariable Long roleId) {
        return success(sysRoleInnerService.selectProjectListByRoleId(roleId));
    }

    /**
     * 查看角色树状图
     * @return
     */
    @Operation(summary = "查看角色树状图")
    @PreAuthorize("@custom.hasAuthority('auth:role:view')")
    @GetMapping("tree")
    public BaseResult<List<BaseSelectVO>> tree(){
        return success(sysRoleInnerService.tree());
    }
}
