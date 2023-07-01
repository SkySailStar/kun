package cn.kun.base.api.service.auth.factory;

import cn.kun.base.api.entity.auth.dto.UserListDTO;
import cn.kun.base.api.entity.auth.vo.SysUserCacheInfoVO;
import cn.kun.base.api.entity.auth.vo.UserDetailVO;
import cn.kun.base.api.service.auth.RemoteUserService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程认证服务-降级处理
 *
 * @author eric
 * @date 2023/5/17 16:28
 */
@Slf4j
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable cause) {

        log.error("远程认证服务：{}", cause.getMessage());
        return new RemoteUserService() {

            @Override
            public BaseResult<List<UserDetailVO>> userInnerList(UserListDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<UserDetailVO>> userOuterList(UserListDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<SysUserCacheInfoVO> userInnerDetail(Long userId) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<SysUserCacheInfoVO> userOuterDetail(Long userId) {
                return BaseResult.fail();
            }

        };
    }
}