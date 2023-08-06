package cn.kun.base.api.service.auth.factory;

import cn.kun.base.api.service.auth.RemoteAuthService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.security.entity.dto.CheckTokenDTO;
import cn.kun.base.core.security.entity.dto.LoginDTO;
import cn.kun.base.core.security.entity.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程认证服务-降级处理
 *
 * @author SkySailStar
 * @date 2023-04-27 16:13
 */
@Slf4j
@Component
public class RemoteAuthFallbackFactory implements FallbackFactory<RemoteAuthService> {
    
    @Override
    public RemoteAuthService create(Throwable cause) {
        
        log.error("远程认证服务：{}", cause.getMessage());
        return new RemoteAuthService() {
            
            @Override
            public BaseResult<LoginVO> login(LoginDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<Void> logout() {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<Boolean> checkToken(CheckTokenDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<String>> permissions() {
                return BaseResult.fail();
            }
            
        };
    }
}
