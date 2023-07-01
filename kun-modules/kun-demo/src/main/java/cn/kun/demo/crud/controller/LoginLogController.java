package cn.kun.demo.crud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.demo.crud.entity.dto.LoginLogPageDTO;
import cn.kun.demo.crud.entity.vo.LoginLogDetailVO;
import cn.kun.demo.crud.entity.vo.LoginLogPageVO;
import cn.kun.demo.crud.service.LoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 登录日志 前端控制器
 * </p>
 *
 * @author 廖航
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
    public BaseResult<Page<LoginLogPageVO>> page(@RequestBody LoginLogPageDTO dto) {
        return success(loginLogService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<LoginLogDetailVO> detail(@PathVariable Long id) {
        return success(loginLogService.detail(id));
    }
    
}
