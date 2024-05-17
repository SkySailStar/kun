package cn.kun.auth.menu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sevnce.auth.menu.entity.dto.MenuAddDTO;
import com.sevnce.auth.menu.entity.dto.MenuEditDTO;
import com.sevnce.auth.menu.entity.dto.MenuPageDTO;
import com.sevnce.auth.menu.entity.dto.UserMenuPermissDTO;
import com.sevnce.auth.menu.entity.po.SysMenu;
import com.sevnce.auth.menu.entity.vo.MenuDetailVO;
import com.sevnce.auth.menu.entity.vo.MenuPageVO;
import com.sevnce.auth.menu.service.SysMenuService;
import com.sevnce.base.core.global.controller.BaseController;
import com.sevnce.base.core.global.entity.BaseResult;
import com.sevnce.base.core.global.entity.vo.BaseSelectVO;
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
    @Autowired
    private SysMenuService sysMenuService;


    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('auth:menu:view')")
    public BaseResult<Page<MenuPageVO>> page(@RequestBody MenuPageDTO dto) {
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
