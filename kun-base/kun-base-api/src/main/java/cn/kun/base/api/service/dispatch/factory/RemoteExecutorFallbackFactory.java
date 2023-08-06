package cn.kun.base.api.service.dispatch.factory;

import cn.kun.base.api.service.dispatch.RemoteExecutorService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.ExecutorPageDTO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorDetailVO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorPageVO;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 远程执行器服务-降级处理
 *
 * @author SkySailStar
 * @date 2023-06-07 10:12
 */
@Slf4j
@Component
public class RemoteExecutorFallbackFactory implements FallbackFactory<RemoteExecutorService> {
    
    @Override
    public RemoteExecutorService create(Throwable cause) {
        
        log.error("远程执行器服务：{}", cause.getMessage());
        return new RemoteExecutorService() {
            
            @Override
            public BaseResult<Page<ExecutorPageVO>> page(ExecutorPageDTO dto) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<ExecutorDetailVO> detail(Integer id) {
                
                return BaseResult.fail();
            }
        };
    }
}
