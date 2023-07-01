package cn.kun.base.api.service.product.factory;

import cn.kun.base.api.entity.product.vo.ProductCategoryVO;
import cn.kun.base.api.service.product.RemoteProductCategoryService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程产品服务-降级处理
 *
 * @author kuangjc
 * @date 2023-05-17 11:14
 */
@Slf4j
@Component
public class RemoteProductCategoryFallbackFactory implements FallbackFactory<RemoteProductCategoryService> {
    
    @Override
    public RemoteProductCategoryService create(Throwable cause) {
        
        log.error("远程产品服务：{}", cause.getMessage());
        return new RemoteProductCategoryService() {

            @Override
            public BaseResult<ProductCategoryVO> simple(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<ProductCategoryVO>> queryListByIds(BaseIdListDTO dto) {
                return BaseResult.fail();
            }

        };
    }
}
