package cn.kun.base.api.service.auth.factory;

import cn.kun.base.api.entity.auth.vo.SysCompanyInfoVO;
import cn.kun.base.api.service.auth.RemoteCompanyService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 远程认证服务-降级处理
 * @author eric
 * @date 2023/5/17 16:23
 */
@Slf4j
@Component
public class RemoteCompanyFallbackFactory implements FallbackFactory<RemoteCompanyService> {

    @Override
    public RemoteCompanyService create(Throwable cause) {

        log.error("远程认证服务：{}", cause.getMessage());
        return new RemoteCompanyService() {

            @Override
            public BaseResult<SysCompanyInfoVO> getCompanyInnerCacheInfoByCompanyId(String companyId) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<SysCompanyInfoVO> getCompanyOuterCacheInfoByCompanyId(String companyId) {
                return BaseResult.fail();
            }

        };
    }
}