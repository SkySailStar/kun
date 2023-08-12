package cn.kun.system.log.controller;

import cn.kun.system.log.entity.vo.LoginLogDetailVO;
import cn.kun.system.log.entity.vo.LoginLogPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.system.log.entity.dto.LoginLogPageDTO;
import cn.kun.system.log.service.LoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * <p>
 * 登录日志 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-09 16:48
 */
@Tag(name = "登录日志")
@RestController
@RequestMapping("/log/login-log")
public class LoginLogController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<LoginLogPageVO>> page(@RequestBody @Valid LoginLogPageDTO dto) {
        return success(loginLogService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<LoginLogDetailVO> detail(@PathVariable Long id) {
        return success(loginLogService.detail(id));
    }
    
}
