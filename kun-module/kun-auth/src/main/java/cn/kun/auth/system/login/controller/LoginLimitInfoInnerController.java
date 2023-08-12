package cn.kun.auth.system.login.controller;

import cn.kun.auth.system.login.entity.dto.CheckDTO;
import cn.kun.auth.system.login.entity.dto.LoginLimitAddDTO;
import cn.kun.auth.system.login.entity.dto.LoginLimitEditDTO;
import cn.kun.auth.system.login.entity.dto.LoginLimitPageDTO;
import cn.kun.auth.system.login.entity.dto.SetStatusDTO;
import cn.kun.auth.system.login.entity.po.LoginLimitInfoInner;
import cn.kun.auth.system.login.entity.vo.CheckResultVO;
import cn.kun.auth.system.login.entity.vo.LoginLimitPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.system.login.service.LoginLimitInfoInnerService;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import cn.kun.base.core.global.controller.BaseController;

import jakarta.validation.Valid;

/**
 * <p>
 * 内部登录限制表 前端控制器
 * </p>
 *
 * @author 胡鹤龄
 * @since 2023-02-27 10:37
 */
@Tag(name = "内部登录限制")
@RestController
@RequestMapping("/system.login/loginLimitInfoInner")
public class LoginLimitInfoInnerController extends BaseController {
    @Autowired
    private LoginLimitInfoInnerService loginLimitInfoInnerService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<LoginLimitPageVO>> page(@RequestBody LoginLimitPageDTO dto) {
        return success(loginLimitInfoInnerService.page(dto));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<LoginLimitInfoInner> add(@RequestBody @Valid LoginLimitAddDTO dto) {
        return success(loginLimitInfoInnerService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<LoginLimitInfoInner> edit(@RequestBody @Valid LoginLimitEditDTO dto) {
        return success(loginLimitInfoInnerService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        loginLimitInfoInnerService.remove(id);
        return success();
    }

    @Operation(summary = "是否限制登录")
    @PostMapping("isLimited")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<CheckResultVO> isLimited(@RequestBody @Valid CheckDTO dto) {
        return success(loginLimitInfoInnerService.isLimited(dto));
    }

    @Operation(summary = "设置登录限制状态")
    @PostMapping("setStatus")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> setStatus(@RequestBody @Valid SetStatusDTO dto) {
        loginLimitInfoInnerService.setStatus(dto);
        return success();
    }
}
