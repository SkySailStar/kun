package cn.kun.base.api.service.product.factory;

import cn.kun.base.api.entity.product.dto.DevicePatrolInstructQueryDTO;
import cn.kun.base.api.entity.product.vo.DevicePatrolInstructQueryVO;
import cn.kun.base.api.entity.product.vo.DevicePatrolInstructSimpleVO;
import cn.kun.base.api.service.product.RemoteDevicePatrolInstructService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程巡检指令服务-降级处理
 *
 * @author kuangjc
 * @date 2023-05-17 11:14
 */
@Slf4j
@Component
public class RemoteDevicePatrolInstructFallbackFactory implements FallbackFactory<RemoteDevicePatrolInstructService> {

    @Override
    public RemoteDevicePatrolInstructService create(Throwable cause) {

        log.error("远程产品信息服务：{}", cause.getMessage());
        return new RemoteDevicePatrolInstructService() {

            @Override
            public BaseResult<List<DevicePatrolInstructQueryVO>> query(DevicePatrolInstructQueryDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<DevicePatrolInstructSimpleVO> simple(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<DevicePatrolInstructSimpleVO>> queryListByIds(BaseIdListDTO dto) {
                return BaseResult.fail();
            }

        };
    }
}
