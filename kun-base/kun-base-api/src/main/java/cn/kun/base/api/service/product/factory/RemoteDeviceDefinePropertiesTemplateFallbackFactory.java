package cn.kun.base.api.service.product.factory;

import cn.kun.base.api.entity.product.dto.DeviceDefinePropertiesTemplateQueryDTO;
import cn.kun.base.api.entity.product.vo.DeviceDefinePropertiesTemplateQueryVO;
import cn.kun.base.api.service.product.RemoteDeviceDefinePropertiesTemplateService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 远程挂载装置定义属性配置模板服务-降级处理
 *
 * @author SkySailStar
 * @date 2023-05-16 11:12
 */
@Slf4j
@Component
public class RemoteDeviceDefinePropertiesTemplateFallbackFactory implements FallbackFactory<RemoteDeviceDefinePropertiesTemplateService> {
    
    @Override
    public RemoteDeviceDefinePropertiesTemplateService create(Throwable cause) {
        
        log.error("远程挂载装置定义属性配置模板服务：{}", cause.getMessage());
        return new RemoteDeviceDefinePropertiesTemplateService() {
            
            @Override
            public BaseResult<List<DeviceDefinePropertiesTemplateQueryVO>> query(DeviceDefinePropertiesTemplateQueryDTO dto) {
                return BaseResult.fail();
            }
        };
    }
}
