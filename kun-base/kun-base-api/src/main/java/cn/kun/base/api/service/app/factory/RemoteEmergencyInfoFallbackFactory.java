package cn.kun.base.api.service.app.factory;

import cn.kun.base.api.service.app.RemoteEmergencyInfoService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 远程预警服务-服务降级
 *
 * @author 廖航
 */
@Slf4j
@Component
public class RemoteEmergencyInfoFallbackFactory implements FallbackFactory<RemoteEmergencyInfoService> {
    
    @Override
    public RemoteEmergencyInfoService create(Throwable cause) {

        log.error("远程预警服务：{}", cause.getMessage());
        return new RemoteEmergencyInfoService() {
            
            @Override
            public BaseResult<Void> pushWarn() {
                return BaseResult.fail();
            }
        };
    }
}