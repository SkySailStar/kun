package cn.kun.auth.security.controller;

import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.security.entity.dto.LoginDTO;
import cn.kun.base.core.security.entity.vo.LoginVO;
import cn.kun.base.core.security.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

/**
 * 登录-控制层
 *
 * @author SkySailStar
 */
@Tag(name = "认证")
@RestController
@RequestMapping("auth")
public class LoginController extends BaseController {

    @Resource
    private LoginService loginService;

    @Operation(summary = "登录")
    @PostMapping("login")
    public BaseResult<LoginVO> login(@RequestBody @Valid LoginDTO dto) throws Exception {
        return success(loginService.login(dto));
    }

    @Operation(summary = "登出")
    @PostMapping("logout")
    public BaseResult<Void> logout() {
        loginService.logout();
        return success();
    }
    
}