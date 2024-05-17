package cn.kun.auth.user.controller;

import com.sevnce.auth.user.entity.dto.UserMenuDTO;
import com.sevnce.auth.user.service.SysUserMenuInnerService;
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
 * 内部用户菜单表 前端控制器
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:22
 */
@Tag(name ="内部用户菜单")
@RestController
@RequestMapping("/system/sysUserMenuInner")
public class SysUserMenuInnerController extends BaseController {
    @Autowired
    private SysUserMenuInnerService sysUserMenuInnerService;

    /**
     * 保存数据
     * @param dto
     * @return
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> save(@RequestBody @Valid UserMenuDTO dto) {
       sysUserMenuInnerService.save(dto);
       return success();
    }

}
