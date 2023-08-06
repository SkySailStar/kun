package cn.kun.base.api.service.system.factory;

import cn.kun.base.api.entity.system.dto.DictDataListDTO;
import cn.kun.base.api.entity.system.vo.DictDataListVO;
import cn.kun.base.api.service.system.RemoteDictService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 远程字典服务-降级处理
 *
 * @author SkySailStar
 * @date 2023-04-27 15:49
 */
@Slf4j
@Component
public class RemoteDictFallbackFactory implements FallbackFactory<RemoteDictService> {
    
    @Override
    public RemoteDictService create(Throwable cause) {
        
        log.error("远程字典服务：{}", cause.getMessage());
        return new RemoteDictService() {
            
            @Override
            public BaseResult<List<BaseSelectVO>> list(String type) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<String> getLabel(String type, String value) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<DictDataListVO>> list(DictDataListDTO dto) {
                
                return BaseResult.fail();
            }
        };
    }
}
