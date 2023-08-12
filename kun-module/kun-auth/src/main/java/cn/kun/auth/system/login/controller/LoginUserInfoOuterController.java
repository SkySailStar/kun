package cn.kun.auth.system.login.controller;

import cn.kun.auth.system.login.entity.dto.LoginUserAddDTO;
import cn.kun.auth.system.login.entity.dto.LoginUserPageDTO;
import cn.kun.auth.system.login.entity.po.LoginUserInfoOuter;
import cn.kun.auth.system.login.entity.vo.LoginUserPageVO;
import cn.kun.auth.system.login.service.LoginUserInfoOuterService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import cn.kun.base.core.global.controller.BaseController;

import javax.validation.Valid;

/**
 * <p>
 * 外部用户登录信息表 前端控制器
 * </p>
 *
 * @author 胡鹤龄
 * @since 2023-02-27 10:39
 */
@Tag(name = "外部用户登录信息")
@RestController
@RequestMapping("/system.login/loginUserInfoOuter")
public class LoginUserInfoOuterController extends BaseController {
    @Autowired
    private LoginUserInfoOuterService loginUserInfoOuterService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<LoginUserPageVO>> page(@RequestBody LoginUserPageDTO dto) {
        return success(loginUserInfoOuterService.page(dto));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<LoginUserInfoOuter> add(@RequestBody @Valid LoginUserAddDTO dto) {
        return success(loginUserInfoOuterService.add(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        loginUserInfoOuterService.remove(id);
        return success();
    }
}
