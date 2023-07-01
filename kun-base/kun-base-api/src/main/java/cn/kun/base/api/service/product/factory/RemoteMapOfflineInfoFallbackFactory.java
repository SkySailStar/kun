package cn.kun.base.api.service.product.factory;

import cn.kun.base.api.entity.product.dto.MapOfflineInfoQueryDTO;
import cn.kun.base.api.entity.product.vo.MapOfflineInfoQueryVO;
import cn.kun.base.api.service.product.RemoteMapOfflineInfoService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 远程离线地图信息服务-降级处理
 *
 * @author 廖航
 * @date 2023-05-16 10:48
 */
@Slf4j
@Component
public class RemoteMapOfflineInfoFallbackFactory implements FallbackFactory<RemoteMapOfflineInfoService> {
    
    @Override
    public RemoteMapOfflineInfoService create(Throwable cause) {
        
        log.error("远程离线地图信息服务：{}", cause.getMessage());
        return new RemoteMapOfflineInfoService() {
            
            @Override
            public BaseResult<List<MapOfflineInfoQueryVO>> query(MapOfflineInfoQueryDTO dto) {
                return BaseResult.fail();
            }
        };
    }
}
