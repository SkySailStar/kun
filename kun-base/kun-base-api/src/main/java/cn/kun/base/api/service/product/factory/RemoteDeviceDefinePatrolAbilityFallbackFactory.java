package cn.kun.base.api.service.product.factory;

import cn.kun.base.api.entity.product.dto.DeviceDefinePatrolAbilityQueryDTO;
import cn.kun.base.api.entity.product.vo.DeviceDefinePatrolAbilityQueryVO;
import cn.kun.base.api.service.product.RemoteDeviceDefinePatrolAbilityService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程挂载装置定义巡检能力服务-降级处理
 *
 * @author 廖航
 * @date 2023-05-16 10:51
 */
@Slf4j
@Component
public class RemoteDeviceDefinePatrolAbilityFallbackFactory implements FallbackFactory<RemoteDeviceDefinePatrolAbilityService> {
    
    @Override
    public RemoteDeviceDefinePatrolAbilityService create(Throwable cause) {
        
        log.error("远程挂载装置定义巡检能力服务：{}", cause.getMessage());
        return new RemoteDeviceDefinePatrolAbilityService() {
            
            @Override
            public BaseResult<List<DeviceDefinePatrolAbilityQueryVO>> query(DeviceDefinePatrolAbilityQueryDTO dto) {
                return BaseResult.fail();
            }
        };
    }
}
