package cn.kun.auth.menu.controller;

import cn.kun.auth.menu.entity.dto.MenuAddDTO;
import cn.kun.auth.menu.entity.dto.MenuEditDTO;
import cn.kun.auth.menu.entity.dto.MenuPageDTO;
import cn.kun.auth.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.auth.menu.service.SysMenuService;
import cn.kun.auth.menu.entity.po.SysMenu;
import cn.kun.auth.menu.entity.vo.MenuDetailVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * 菜单表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "菜单")
@RestController
@RequestMapping("/system/sysMenu")
public class SysMenuController extends BaseController {
    @Resource
    private SysMenuService sysMenuService;


    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('auth:menu:view')")
    public BaseResult<Page> page(@RequestBody MenuPageDTO dto) {
        return success(sysMenuService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:menu:view')")
    public BaseResult<MenuDetailVO> detail(@PathVariable Long id) {
        return BaseResult.success(sysMenuService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('auth:menu:add')")
    public BaseResult<SysMenu> add(@RequestBody @Valid MenuAddDTO dto) {
        return success(sysMenuService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('auth:menu:edit')")
    public BaseResult<SysMenu> edit(@RequestBody @Valid MenuEditDTO dto) {
        return success(sysMenuService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('auth:menu:remove')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        sysMenuService.remove(id);
        return success();
    }

    @Operation(summary = "查看上级菜单")
    @PostMapping("menuList")
    @PreAuthorize("@custom.hasAuthority('auth:menu:view')")
    public BaseResult<List<BaseSelectVO>> menuList(@RequestBody UserMenuPermissDTO dto) {
        return success(sysMenuService.menuList(dto));
    }

    @Operation(summary = "获取登陆人的系统名称")
    @PostMapping("serviceCode")
    @PreAuthorize("@custom.hasAuthority('auth:menu:view')")
    public BaseResult<List<BaseSelectVO>> serviceCode() {
        return success(sysMenuService.serviceCode());
    }

    /**
     * 查看菜单树状图
     * @return
     */
    @Operation(summary = "查看菜单树状图")
    @PreAuthorize("@custom.hasAuthority('auth:menu:view')")
    @GetMapping("tree")
    public BaseResult<List<BaseSelectVO>> tree(){
        return success(sysMenuService.tree());
    }
}
