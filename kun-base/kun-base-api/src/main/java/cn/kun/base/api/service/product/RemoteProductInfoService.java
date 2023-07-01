package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.dto.ProductInfoQueryDTO;
import cn.kun.base.api.entity.product.vo.ProductInfoQueryVO;
import cn.kun.base.api.service.product.factory.RemoteProductInfoFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 * 远程产品信息 服务类
 * </p>
 *
 * @author kuangjc
 * @since 2023-03-06 17:08
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteProductInfoFallbackFactory.class)
public interface RemoteProductInfoService {

    /**
     * 产品基本信息 dto
     * 产品基本信息返回值
     */
    @PostMapping("product/productInfo/info")
    BaseResult<ProductInfoQueryVO>  info(ProductInfoQueryDTO dto);

}
