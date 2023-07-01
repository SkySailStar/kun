package cn.kun.base.api.service.product;

import cn.kun.base.api.service.product.factory.RemoteDeviceInfoFallbackFactory;
import cn.kun.base.api.entity.product.dto.DeviceInfoQueryDTO;
import cn.kun.base.api.entity.product.vo.DeviceInfoQueryVO;
import cn.kun.base.api.entity.product.vo.DeviceInfoSimpleVO;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 远程挂载装置信息服务
 *
 * @author 廖航
 * @date 2023-03-27 17:31
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteDeviceInfoFallbackFactory.class)
public interface RemoteDeviceInfoService {

    /**
     * 查询
     * @param dto 查询-传入值
     * @return 结果
     */
    @PostMapping("device/deviceInfo/query")
    BaseResult<List<DeviceInfoQueryVO>> query(@RequestBody DeviceInfoQueryDTO dto);

    /**
     * 获取单项
     * @param id 主键
     * @return 单项
     */
    @GetMapping("device/deviceInfo/simple/{id}")
    BaseResult<DeviceInfoSimpleVO> simple(@PathVariable("id") Long id);

    /**
     * 获取多项
     * @param dto 主键列表-公用传入值
     * @return 多项
     */
    @PostMapping("device/deviceInfo/simple/queryListByIds")
    BaseResult<List<DeviceInfoSimpleVO>> queryListByIds(@RequestBody @Valid BaseIdListDTO dto);
}
