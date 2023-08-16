package cn.kun.auth.security.controller;

import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.security.entity.LoginUser;
import cn.kun.base.core.security.entity.dto.CheckTokenDTO;
import cn.kun.base.core.security.entity.dto.LoginDTO;
import cn.kun.base.core.security.entity.vo.LoginVO;
import cn.kun.base.core.security.service.AuthService;
import cn.kun.base.core.security.util.AuthHelp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 认证-控制层
 *
 * @author SkySailStar
 */
@Tag(name = "认证")
@RestController
@RequestMapping("auth")
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "登录")
    @PostMapping("login")
    public BaseResult<LoginVO> login(@RequestBody @Valid LoginDTO dto) throws Exception {
        return success(authService.login(dto));
    }

    @Operation(summary = "登出")
    @PostMapping("logout")
    public BaseResult<Void> logout() {
        authService.logout();
        return success();
    }

    @Operation(summary = "验证token是否在有效期内")
    @PostMapping ("check/token")
    public BaseResult<Boolean> checkToken(@RequestBody CheckTokenDTO dto) {
        return success(authService.checkToken(dto));
    }
    
    @Operation(summary = "获取权限列表")
    @GetMapping("permissions")
    public BaseResult<List<String>> permissions() {
        return success(authService.permissions());
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("getUserInfo")
    public BaseResult<LoginUser> getUserInfo() {
        return success(AuthHelp.getLoginUser());
    }
    
    @Operation(summary = "获取实时用户信息")
    @GetMapping("real/user-info")
    private BaseResult<LoginUser> getRealUserInfo() {
        return success(authService.getRealUserInfo());
    }
    
}