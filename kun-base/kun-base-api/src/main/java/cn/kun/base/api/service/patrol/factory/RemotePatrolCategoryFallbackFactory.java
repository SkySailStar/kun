package cn.kun.base.api.service.patrol.factory;

import cn.kun.base.api.entity.patrol.dto.PatrolCategoryQueryDTO;
import cn.kun.base.api.entity.patrol.vo.PatrolCategoryVO;
import cn.kun.base.api.service.patrol.RemotePatrolCategoryService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程巡检类型服务-降级处理
 *
 * @author kuangjc
 * @date 2023-05-17 11:14
 */
@Slf4j
@Component
public class RemotePatrolCategoryFallbackFactory implements FallbackFactory<RemotePatrolCategoryService> {
    
    @Override
    public RemotePatrolCategoryService create(Throwable cause) {
        
        log.error("远程巡检类型服务：{}", cause.getMessage());
        return new RemotePatrolCategoryService() {

            @Override
            public BaseResult<List<PatrolCategoryVO>> query(PatrolCategoryQueryDTO dto) {
                return BaseResult.fail();
            }

        };
    }
}
