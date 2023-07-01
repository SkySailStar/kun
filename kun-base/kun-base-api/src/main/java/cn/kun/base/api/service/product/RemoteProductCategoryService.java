package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.vo.ProductCategoryVO;
import cn.kun.base.api.service.patrol.factory.RemotePatrolCategoryFallbackFactory;
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
 * 远程产品类型服务
 *
 * @author kuangjc
 * @date 2023-05-22 17:41
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemotePatrolCategoryFallbackFactory.class)
public interface RemoteProductCategoryService {

    @GetMapping("product/productCategory/simple/{id}")
    BaseResult<ProductCategoryVO> simple(@PathVariable("id") Long id);

    @PostMapping("product/productCategory/simple/queryListByIds")
    BaseResult<List<ProductCategoryVO>> queryListByIds(@RequestBody @Valid BaseIdListDTO dto);

}
