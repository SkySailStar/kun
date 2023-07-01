package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.dto.MapInfoQueryDTO;
import cn.kun.base.api.entity.product.vo.MapInfoQueryVO;
import cn.kun.base.api.entity.product.vo.MapInfoSimpleVO;
import cn.kun.base.api.service.product.factory.RemoteMapInfoFallbackFactory;
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
 * 远程机器人地图信息服务
 *
 * @author 廖航
 * @date 2023-03-27 17:44
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteMapInfoFallbackFactory.class)
public interface RemoteMapInfoService {

    /**
     * 查询
     * @param dto 查询-传入值
     * @return 结果
     */
    @PostMapping("map/mapInfo/query")
    BaseResult<List<MapInfoQueryVO>> query(@RequestBody MapInfoQueryDTO dto);

    /**
     * 获取单项
     * @param id 主键
     * @return 单项
     */
    @GetMapping("map/mapInfo/simple/{id}")
    BaseResult<MapInfoSimpleVO> simple(@PathVariable("id") Long id);

    /**
     * 获取多项
     * @param dto 主键列表-公用传入值
     * @return 多项
     */
    @PostMapping("map/mapInfo/simple/queryListByIds")
    BaseResult<List<MapInfoSimpleVO>> queryListByIds(@RequestBody @Valid BaseIdListDTO dto);
    
}
