package cn.kun.base.api.service.assets;

import cn.kun.base.api.entity.assets.vo.FactoryRegionManageSimpleVO;
import cn.kun.base.api.service.assets.factory.RemoteRegionManageFallbackFactory;
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
 * 远程区域管理服务
 *
 * @author 胡鹤龄
 * @date 2023-05-16 17:10
 */
@FeignClient(value = ServiceConstants.ASSETS, fallbackFactory = RemoteRegionManageFallbackFactory.class)
public interface RemoteRegionManageService {

    /**
     * 通过区域管理id查询台账主设备信息
     * @param id
     * @return
     */
    @GetMapping("/assets/region/manage/simple/{id}")
    BaseResult<FactoryRegionManageSimpleVO> detailSimpleVO(@PathVariable("id") Long id);

    /**
     * 通过台账id列表查询台账主设备信息
     * @param dto
     * @return
     */
    @PostMapping("/assets/region/manage/simple/queryListByIds")
    BaseResult<List<FactoryRegionManageSimpleVO>> queryVOListByIds(@RequestBody BaseIdListDTO dto);
}
