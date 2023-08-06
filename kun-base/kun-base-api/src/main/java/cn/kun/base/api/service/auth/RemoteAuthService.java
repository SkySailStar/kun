package cn.kun.base.api.service.auth;

import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.api.service.auth.factory.RemoteAuthFallbackFactory;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.security.entity.dto.CheckTokenDTO;
import cn.kun.base.core.security.entity.dto.LoginDTO;
import cn.kun.base.core.security.entity.vo.LoginVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 远程认证服务
 *
 * @author SkySailStar
 * @date 2023-01-07 16:24
 */
@FeignClient(value = ServiceConstants.AUTH, fallbackFactory = RemoteAuthFallbackFactory.class)
public interface RemoteAuthService {

    /**
     * 登录
     *
     * @param dto 系统用户
     * @return 结果
     */
    @PostMapping("auth/login")
    BaseResult<LoginVO> login(@RequestBody LoginDTO dto);

    /**
     * 登出
     * @return 结果
     */
    @PostMapping("auth/logout")
    BaseResult<Void> logout();
    
    /**
     * 验证token是否在有效期内
     *
     * @param dto 认证信息
     * @return 结果
     */
    @PostMapping("auth/check/token")
    BaseResult<Boolean> checkToken(CheckTokenDTO dto);

    /**
     * 获取权限列表
     * @return 权限列表
     */
    @GetMapping("auth/permissions")
    BaseResult<List<String>> permissions();
    
}
