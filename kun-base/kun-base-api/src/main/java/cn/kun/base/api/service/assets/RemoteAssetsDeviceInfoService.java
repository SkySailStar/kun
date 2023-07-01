package cn.kun.base.api.service.assets;

import cn.kun.base.api.entity.assets.vo.AssetsDeviceInfoSimpleVO;
import cn.kun.base.api.service.assets.factory.RemoteAssetsDeviceInfoFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * 远程台账主设备服务
 *
 * @author 胡鹤龄
 * @date 2023-05-16 16:01
 */
@FeignClient(value = ServiceConstants.ASSETS, fallbackFactory = RemoteAssetsDeviceInfoFallbackFactory.class)
public interface RemoteAssetsDeviceInfoService {

    /**
     * 通过台账id查询台账主设备信息
     * @param id
     * @return
     */
    @GetMapping("/assets/device/info/simple/{id}")
    BaseResult<AssetsDeviceInfoSimpleVO> detailSimpleVO(@PathVariable("id") Long id);

    /**
     * 通过台账id列表查询台账主设备信息
     * @param dto
     * @return
     */
    @PostMapping("/assets/device/info/simple/queryListByIds")
    BaseResult<List<AssetsDeviceInfoSimpleVO>> queryVOListByIds(@RequestBody BaseIdListDTO dto);
}
