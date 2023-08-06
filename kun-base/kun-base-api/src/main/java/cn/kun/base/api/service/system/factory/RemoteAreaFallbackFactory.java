package cn.kun.base.api.service.system.factory;

import cn.kun.base.api.service.system.RemoteAreaService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 远程区域服务-降级处理
 *
 * @author SkySailStar
 * @date 2023-04-27 13:57
 */
@Slf4j
@Component
public class RemoteAreaFallbackFactory implements FallbackFactory<RemoteAreaService> {

    @Override
    public RemoteAreaService create(Throwable cause) {
        
        log.error("远程区域服务：{}", cause.getMessage());
        return new RemoteAreaService() {
            
            @Override
            public BaseResult<String> getNameById(Long id) {
                
                return BaseResult.fail();
            }
        };
    }
    
}
