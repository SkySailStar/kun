package cn.kun.dispatch.feign.controller;

import cn.kun.base.api.service.auth.RemoteAuthService;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.security.entity.dto.LoginDTO;
import cn.kun.base.core.security.entity.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证-控制层
 *
 * @author 廖航
 */
@Tag(name = "认证")
@RestController
@RequestMapping("auth")
public class AuthController extends BaseController {

    @Autowired
    private RemoteAuthService remoteAuthService;

    @Operation(summary = "登录")
    @PostMapping("login")
    public BaseResult<LoginVO> login(@RequestBody @Valid LoginDTO dto) {
        return remoteAuthService.login(dto);
    }
    
}