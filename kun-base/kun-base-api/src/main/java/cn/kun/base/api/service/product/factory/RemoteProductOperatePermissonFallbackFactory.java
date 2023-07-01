package cn.kun.base.api.service.product.factory;

import cn.kun.base.api.entity.product.dto.ProductOperatePermissionVeryfyDTO;
import cn.kun.base.api.service.product.RemoteProductOperatePermissionService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 远程产品操作权限服务-降级处理
 *
 * @author kuangjc
 * @date 2023-05-17 11:14
 */
@Slf4j
@Component
public class RemoteProductOperatePermissonFallbackFactory implements FallbackFactory<RemoteProductOperatePermissionService> {
    
    @Override
    public RemoteProductOperatePermissionService create(Throwable cause) {
        
        log.error("远程产品操作权限服务：{}", cause.getMessage());
        return new RemoteProductOperatePermissionService() {

            @Override
            public BaseResult<Boolean> verifyProductOperatePermission(ProductOperatePermissionVeryfyDTO dto) {
                return BaseResult.fail();
            }

        };
    }
}
