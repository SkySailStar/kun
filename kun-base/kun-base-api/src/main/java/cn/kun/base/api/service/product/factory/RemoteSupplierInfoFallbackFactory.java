package cn.kun.base.api.service.product.factory;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.product.dto.SupplierInfoPageDTO;
import cn.kun.base.api.entity.product.vo.DeviceTypePageVO;
import cn.kun.base.api.service.product.RemoteSupplierInfoService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 供应商信息服务-降级处理
 *
 * @author kuangjc
 * @date 2023-05-17 11:14
 */
@Slf4j
@Component
public class RemoteSupplierInfoFallbackFactory implements FallbackFactory<RemoteSupplierInfoService> {
    
    @Override
    public RemoteSupplierInfoService create(Throwable cause) {
        
        log.error("供应商信息服务：{}", cause.getMessage());
        return new RemoteSupplierInfoService() {

            @Override
            public BaseResult<Page<DeviceTypePageVO>> page(SupplierInfoPageDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<BaseSelectVO>> list() {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<Void> remove(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<Void> removeBatch(BaseIdListDTO dto) {
                return BaseResult.fail();
            }

        };
    }
}
