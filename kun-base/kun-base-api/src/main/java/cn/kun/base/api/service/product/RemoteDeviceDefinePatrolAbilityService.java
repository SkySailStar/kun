package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.dto.DeviceDefinePatrolAbilityQueryDTO;
import cn.kun.base.api.entity.product.vo.DeviceDefinePatrolAbilityQueryVO;
import cn.kun.base.api.service.product.factory.RemoteDeviceDefinePatrolAbilityFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 远程挂载装置定义巡检能力服务
 *
 * @author SkySailStar
 * @date 2023-03-27 17:39
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteDeviceDefinePatrolAbilityFallbackFactory.class)
public interface RemoteDeviceDefinePatrolAbilityService {

    /**
     * 查询
     * @param dto 查询-传入值
     * @return 结果
     */
    @PostMapping("device/deviceDefinePatrolAbility/query")
    BaseResult<List<DeviceDefinePatrolAbilityQueryVO>> query(@RequestBody DeviceDefinePatrolAbilityQueryDTO dto);
    
}
