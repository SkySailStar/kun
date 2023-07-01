package cn.kun.base.api.service.product.factory;

import cn.kun.base.api.entity.product.dto.MapInfoQueryDTO;
import cn.kun.base.api.entity.product.vo.MapInfoQueryVO;
import cn.kun.base.api.entity.product.vo.MapInfoSimpleVO;
import cn.kun.base.api.service.product.RemoteMapInfoService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程机器人地图信息服务-降级处理
 *
 * @author 廖航
 * @date 2023-05-16 10:42
 */
@Slf4j
@Component
public class RemoteMapInfoFallbackFactory implements FallbackFactory<RemoteMapInfoService> {
    
    @Override
    public RemoteMapInfoService create(Throwable cause) {
        
        log.error("远程机器人地图信息服务：{}", cause.getMessage());
        return new RemoteMapInfoService() {
            
            @Override
            public BaseResult<List<MapInfoQueryVO>> query(MapInfoQueryDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<MapInfoSimpleVO> simple(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<MapInfoSimpleVO>> queryListByIds(BaseIdListDTO dto) {
                return BaseResult.fail();
            }

        };
    }
}
