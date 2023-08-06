package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.dto.MapOfflineInfoQueryDTO;
import cn.kun.base.api.entity.product.vo.MapOfflineInfoQueryVO;
import cn.kun.base.api.service.product.factory.RemoteMapOfflineInfoFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 远程离线地图信息服务
 *
 * @author SkySailStar
 * @date 2023-03-27 17:47
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteMapOfflineInfoFallbackFactory.class)
public interface RemoteMapOfflineInfoService {

    /**
     * 查询
     * @param dto 查询-传入值
     * @return 结果
     */
    @PostMapping("map/mapOfflineInfo/query")
    BaseResult<List<MapOfflineInfoQueryVO>> query(@RequestBody MapOfflineInfoQueryDTO dto);
}
