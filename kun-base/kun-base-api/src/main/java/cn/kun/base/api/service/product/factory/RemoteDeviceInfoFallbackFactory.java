package cn.kun.base.api.service.product.factory;

import cn.kun.base.api.entity.product.dto.DeviceInfoQueryDTO;
import cn.kun.base.api.entity.product.vo.DeviceInfoQueryVO;
import cn.kun.base.api.entity.product.vo.DeviceInfoSimpleVO;
import cn.kun.base.api.service.product.RemoteDeviceInfoService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 远程挂载装置信息服务-降级处理
 *
 * @author 廖航
 * @date 2023-05-16 11:16
 */
@Slf4j
@Component
public class RemoteDeviceInfoFallbackFactory implements FallbackFactory<RemoteDeviceInfoService> {
    
    @Override
    public RemoteDeviceInfoService create(Throwable cause) {
        
        log.error("远程挂载装置信息服务：{}", cause.getMessage());
        return new RemoteDeviceInfoService() {
            
            @Override
            public BaseResult<List<DeviceInfoQueryVO>> query(DeviceInfoQueryDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<DeviceInfoSimpleVO> simple(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<DeviceInfoSimpleVO>> queryListByIds(BaseIdListDTO dto) {
                return BaseResult.fail();
            }
        };
    }
}
