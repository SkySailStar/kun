package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.dto.DeviceDefinePropertiesTemplateQueryDTO;
import cn.kun.base.api.entity.product.vo.DeviceDefinePropertiesTemplateQueryVO;
import cn.kun.base.api.service.product.factory.RemoteDeviceDefinePropertiesTemplateFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 远程挂载装置定义属性配置模板服务
 *
 * @author 廖航
 * @date 2023-03-27 17:34
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteDeviceDefinePropertiesTemplateFallbackFactory.class)
public interface RemoteDeviceDefinePropertiesTemplateService {

    /**
     * 查询
     * @param dto 查询-传入值
     * @return 结果
     */
    @PostMapping("device/deviceDefinePropertiesTemplate/query")
    BaseResult<List<DeviceDefinePropertiesTemplateQueryVO>> query(@RequestBody DeviceDefinePropertiesTemplateQueryDTO dto);
}
