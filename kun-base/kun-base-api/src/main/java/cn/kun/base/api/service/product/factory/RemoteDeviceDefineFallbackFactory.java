package cn.kun.base.api.service.product.factory;

import cn.kun.base.api.entity.product.dto.DeviceDefineQueryDTO;
import cn.kun.base.api.entity.product.vo.DeviceDefineQueryVO;
import cn.kun.base.api.service.product.RemoteDeviceDefineService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 远程挂载装置定义服务-降级处理
 *
 * @author SkySailStar
 * @date 2023-05-16 11:14
 */
@Slf4j
@Component
public class RemoteDeviceDefineFallbackFactory implements FallbackFactory<RemoteDeviceDefineService> {
    
    @Override
    public RemoteDeviceDefineService create(Throwable cause) {
        
        log.error("远程挂载装置定义服务：{}", cause.getMessage());
        return new RemoteDeviceDefineService() {
            
            @Override
            public BaseResult<List<DeviceDefineQueryVO>> query(DeviceDefineQueryDTO dto) {
                return BaseResult.fail();
            }
        };
    }
}
