package cn.kun.base.api.service.assets.factory;


import cn.kun.base.api.entity.assets.vo.AssetsManageListVO;
import cn.kun.base.api.entity.assets.vo.AssetsManageSimpleVO;
import cn.kun.base.api.service.assets.RemoteAssetsManagementService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 批量查询台账信息
 *
 * @author 胡鹤龄
 * @date 2023-05-16 13:50
 */
@Slf4j
@Component
public class RemoteAssetsManagementFallbackFactory implements FallbackFactory<RemoteAssetsManagementService> {
    @Override
    public RemoteAssetsManagementService create(Throwable cause) {

        log.error("远程台账管理服务：{}", cause.getMessage());
        return new RemoteAssetsManagementService() {

            @Override
            public BaseResult<List<AssetsManageListVO>> assetsInfoByUser(String projectNo) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<AssetsManageSimpleVO> detailSimpleVO(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<AssetsManageSimpleVO>> queryVOListByIds(BaseIdListDTO dto) {
                return BaseResult.fail();
            }

        };
        }
}
