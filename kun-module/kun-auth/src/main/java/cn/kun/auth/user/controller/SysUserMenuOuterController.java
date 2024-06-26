package cn.kun.auth.user.controller;

import com.sevnce.auth.user.entity.dto.UserMenuDTO;
import com.sevnce.auth.user.service.SysUserMenuOuterService;
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
 * 外部用户菜单表 前端控制器
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:21
 */
@Tag(name = "外部用户菜单")
@RestController
@RequestMapping("/system/sysUserMenuOuter")
public class SysUserMenuOuterController extends BaseController {
    @Autowired
    private SysUserMenuOuterService sysUserMenuOuterService;

    /**
     * 保存数据
     * @param dto
     * @return
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> save(@RequestBody @Valid UserMenuDTO dto) {
        sysUserMenuOuterService.save(dto);
        return success();
    }


    /**
     * 通过用户id获取用户菜单信息
     * @param dto
     * @return
     */
//    @PostMapping("getMenuIdsByUserId")
//    public BaseResult getMenuIdsByUserId(@RequestBody SysUserMenuDTO dto){
//        return success(sysUserMenuOuterService.getMenuIdsByUserId(dto));
//    }
}
