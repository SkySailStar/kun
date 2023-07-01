package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.dto.ProductOperatePermissionVeryfyDTO;
import cn.kun.base.api.service.product.factory.RemoteProductOperatePermissonFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 * 远程产品操作权限 服务类
 * </p>
 *
 * @author kuangjc
 * @since 2023-03-06 17:08
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteProductOperatePermissonFallbackFactory.class)
public interface RemoteProductOperatePermissionService {

    /**
     * 产品操作权限验证 dto
     * 方法：验证用户是否拥有产品操作权限
     */
    @PostMapping("product/productOperatePermission/verify")
    BaseResult<Boolean>  verifyProductOperatePermission(ProductOperatePermissionVeryfyDTO dto);

}
