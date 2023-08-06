package cn.kun.base.api.service.product.factory;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.product.dto.DeviceTypePageDTO;
import cn.kun.base.api.entity.product.dto.DeviceTypeQueryDTO;
import cn.kun.base.api.entity.product.vo.DeviceTypePageVO;
import cn.kun.base.api.entity.product.vo.DeviceTypeQueryVO;
import cn.kun.base.api.entity.product.vo.DeviceTypeSimpleVO;
import cn.kun.base.api.service.product.RemoteDeviceTypeService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程挂载装置类型服务-降级处理
 *
 * @author SkySailStar
 * @date 2023-05-16 11:20
 */
@Slf4j
@Component
public class RemoteDeviceTypeFallbackFactory implements FallbackFactory<RemoteDeviceTypeService> {
    
    @Override
    public RemoteDeviceTypeService create(Throwable cause) {
        
        log.error("远程挂载装置类型服务：{}", cause.getMessage());
        return new RemoteDeviceTypeService() {
            
            @Override
            public BaseResult<Page<DeviceTypePageVO>> page(DeviceTypePageDTO dto) {
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
            public BaseResult<List<DeviceTypeQueryVO>> query(DeviceTypeQueryDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<DeviceTypeSimpleVO> simple(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<DeviceTypeSimpleVO>> queryListByIds(BaseIdListDTO dto) {
                return BaseResult.fail();
            }

        };
    }
}
