package cn.kun.base.api.service.patrol.factory;

import cn.kun.base.api.entity.patrol.dto.PatrolAbilityQueryDTO;
import cn.kun.base.api.entity.patrol.vo.PatrolAbilityVO;
import cn.kun.base.api.service.patrol.RemotePatrolAbilityService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程巡检能力服务-降级处理
 *
 * @author kuangjc
 * @date 2023-05-17 11:14
 */
@Slf4j
@Component
public class RemotePatrolAbilityFallbackFactory implements FallbackFactory<RemotePatrolAbilityService> {
    
    @Override
    public RemotePatrolAbilityService create(Throwable cause) {
        
        log.error("远程巡检能力服务：{}", cause.getMessage());
        return new RemotePatrolAbilityService() {

            @Override
            public BaseResult<List<PatrolAbilityVO>> query(PatrolAbilityQueryDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<PatrolAbilityVO> detail(Long id) {
                return BaseResult.fail();
            }

        };
    }
}
