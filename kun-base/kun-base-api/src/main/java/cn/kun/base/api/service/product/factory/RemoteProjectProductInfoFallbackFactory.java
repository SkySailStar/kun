package cn.kun.base.api.service.product.factory;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.product.dto.ProjectProductInfoListDTO;
import cn.kun.base.api.entity.product.dto.ProjectProductInfoPageDTO;
import cn.kun.base.api.entity.product.vo.ProjectProductInfoPageVO;
import cn.kun.base.api.entity.product.vo.ProjectProductInfoSimpleVO;
import cn.kun.base.api.service.product.RemoteProjectProductInfoService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程项目挂靠产品信息服务-降级处理
 *
 * @author 廖航
 * @date 2023-05-16 10:42
 */
@Slf4j
@Component
public class RemoteProjectProductInfoFallbackFactory implements FallbackFactory<RemoteProjectProductInfoService> {
    
    @Override
    public RemoteProjectProductInfoService create(Throwable cause) {
        
        log.error("远程项目挂靠产品信息服务：{}", cause.getMessage());
        return new RemoteProjectProductInfoService() {

            @Override
            public BaseResult<Page<ProjectProductInfoPageVO>> page(ProjectProductInfoPageDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<Void> remove(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<BaseSelectVO>> list(ProjectProductInfoListDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<ProjectProductInfoSimpleVO> detailSimpleVO(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<ProjectProductInfoSimpleVO>> queryVOListByIds(BaseIdListDTO dto) {
                return BaseResult.fail();
            }

        };
    }
}
