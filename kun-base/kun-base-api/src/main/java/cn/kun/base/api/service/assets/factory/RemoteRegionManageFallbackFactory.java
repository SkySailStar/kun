package cn.kun.base.api.service.assets.factory;

import cn.kun.base.api.entity.assets.vo.FactoryRegionManageSimpleVO;
import cn.kun.base.api.service.assets.RemoteRegionManageService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程区域管理服务
 *
 * @author 胡鹤龄
 * @date 2023-05-16 17:11
 */
@Slf4j
@Component
public class RemoteRegionManageFallbackFactory implements FallbackFactory<RemoteRegionManageService> {

    @Override
    public RemoteRegionManageService create(Throwable cause) {

        log.error("远程区域管理服务：{}", cause.getMessage());
        return new RemoteRegionManageService() {

            @Override
            public BaseResult<FactoryRegionManageSimpleVO> detailSimpleVO(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<FactoryRegionManageSimpleVO>> queryVOListByIds(BaseIdListDTO dto) {
                return BaseResult.fail();
            }

        };
    }
}
