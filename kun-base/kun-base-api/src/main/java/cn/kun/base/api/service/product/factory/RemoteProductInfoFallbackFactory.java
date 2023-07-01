package cn.kun.base.api.service.product.factory;

import cn.kun.base.api.entity.product.dto.ProductInfoQueryDTO;
import cn.kun.base.api.entity.product.vo.ProductInfoQueryVO;
import cn.kun.base.api.service.product.RemoteProductInfoService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 远程产品信息服务-降级处理
 *
 * @author kuangjc
 * @date 2023-05-17 11:14
 */
@Slf4j
@Component
public class RemoteProductInfoFallbackFactory implements FallbackFactory<RemoteProductInfoService> {
    
    @Override
    public RemoteProductInfoService create(Throwable cause) {
        
        log.error("远程产品信息服务：{}", cause.getMessage());
        return new RemoteProductInfoService() {

            @Override
            public BaseResult<ProductInfoQueryVO> info(ProductInfoQueryDTO dto) {
                return BaseResult.fail();
            }

        };
    }
}
