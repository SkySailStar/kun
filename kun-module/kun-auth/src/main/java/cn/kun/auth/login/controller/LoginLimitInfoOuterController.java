package cn.kun.auth.login.controller;

import cn.kun.auth.login.entity.dto.CheckDTO;
import cn.kun.auth.login.entity.dto.LoginLimitAddDTO;
import cn.kun.auth.login.entity.dto.LoginLimitEditDTO;
import cn.kun.auth.login.entity.dto.LoginLimitPageDTO;
import cn.kun.auth.login.entity.dto.SetStatusDTO;
import cn.kun.auth.login.entity.po.LoginLimitInfoOuter;
import cn.kun.auth.login.entity.vo.CheckResultVO;
import cn.kun.auth.login.entity.vo.LoginLimitPageVO;
import cn.kun.auth.login.service.LoginLimitInfoOuterService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import cn.kun.base.core.global.controller.BaseController;

import jakarta.validation.Valid;

/**
 * <p>
 * 外部登录限制表 前端控制器
 * </p>
 *
 * @author 胡鹤龄
 * @since 2023-02-27 10:38
 */
@Tag(name = "外部登录限制")
@RestController
@RequestMapping("/system.login/loginLimitInfoOuter")
public class LoginLimitInfoOuterController extends BaseController {
    @Resource
    private LoginLimitInfoOuterService loginLimitInfoOuterService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<LoginLimitPageVO>> page(@RequestBody LoginLimitPageDTO dto) {
        return success(loginLimitInfoOuterService.page(dto));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<LoginLimitInfoOuter> add(@RequestBody @Valid LoginLimitAddDTO dto) {
        return success(loginLimitInfoOuterService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<LoginLimitInfoOuter> edit(@RequestBody @Valid LoginLimitEditDTO dto) {
        return success(loginLimitInfoOuterService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        loginLimitInfoOuterService.remove(id);
        return success();
    }

    @Operation(summary = "是否限制登录")
    @PostMapping("isLimited")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<CheckResultVO> isLimited(@RequestBody @Valid CheckDTO dto) {
        return success(loginLimitInfoOuterService.isLimited(dto));
    }

    @Operation(summary = "设置登录限制状态")
    @PostMapping("setStatus")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> setStatus(@RequestBody @Valid SetStatusDTO dto) {
        loginLimitInfoOuterService.setStatus(dto);
        return success();
    }
}
