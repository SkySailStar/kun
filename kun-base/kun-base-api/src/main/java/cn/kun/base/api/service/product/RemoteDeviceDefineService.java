package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.dto.DeviceDefineQueryDTO;
import cn.kun.base.api.entity.product.vo.DeviceDefineQueryVO;
import cn.kun.base.api.service.product.factory.RemoteDeviceDefineFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 远程挂载装置定义服务
 *
 * @author 廖航
 * @date 2023-03-27 17:42
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteDeviceDefineFallbackFactory.class)
public interface RemoteDeviceDefineService {

    /**
     * 查询
     * @param dto 查询-传入值
     * @return 结果
     */
    @PostMapping("device/deviceDefine/query")
    BaseResult<List<DeviceDefineQueryVO>> query(@RequestBody DeviceDefineQueryDTO dto);
}
