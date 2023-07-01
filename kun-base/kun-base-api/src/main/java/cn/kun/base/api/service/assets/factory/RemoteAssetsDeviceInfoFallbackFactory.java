package cn.kun.base.api.service.assets.factory;

import cn.kun.base.api.entity.assets.vo.AssetsDeviceInfoSimpleVO;
import cn.kun.base.api.service.assets.RemoteAssetsDeviceInfoService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程台账主设备服务
 *
 * @author 胡鹤龄
 * @date 2023-05-16 16:02
 */
@Slf4j
@Component
public class RemoteAssetsDeviceInfoFallbackFactory implements FallbackFactory<RemoteAssetsDeviceInfoService> {
    @Override
    public RemoteAssetsDeviceInfoService create(Throwable cause) {


        log.error("远程台账主设备服务：{}", cause.getMessage());
        return new RemoteAssetsDeviceInfoService() {

            @Override
            public BaseResult<AssetsDeviceInfoSimpleVO> detailSimpleVO(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<AssetsDeviceInfoSimpleVO>> queryVOListByIds(BaseIdListDTO dto) {
                return BaseResult.fail();
            }

        };
        }

}
