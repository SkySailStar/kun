package cn.kun.base.api.service.assets;

import cn.kun.base.api.entity.assets.vo.AssetsManageListVO;
import cn.kun.base.api.entity.assets.vo.AssetsManageSimpleVO;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.api.service.assets.factory.RemoteAssetsManagementFallbackFactory;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author eric
 * @date 2023/4/6 16:34
 */
@FeignClient(value = ServiceConstants.ASSETS, fallbackFactory = RemoteAssetsManagementFallbackFactory.class)
public interface RemoteAssetsManagementService {

    /**
     * 根据登陆人获取所有台账信息
     *
     * @return
     */
    @RequestMapping("assets/management/assetsInfoByUser")
    BaseResult<List<AssetsManageListVO>> assetsInfoByUser(@RequestParam(name = "projectNo" ,required = false)String projectNo);


    /**
     * 通过台账id查询台账管理详情
     * @param id
     * @return
     */
    @GetMapping("assets/management/simple/{id}")
    BaseResult<AssetsManageSimpleVO> detailSimpleVO(@PathVariable("id") Long id);

    /**
     * 通过台账id查询台账管理详情
     * @param dto
     * @return
     */
    @PostMapping("assets/management/simple/queryListByIds")
    BaseResult<List<AssetsManageSimpleVO>> queryVOListByIds(@RequestBody BaseIdListDTO dto);
}