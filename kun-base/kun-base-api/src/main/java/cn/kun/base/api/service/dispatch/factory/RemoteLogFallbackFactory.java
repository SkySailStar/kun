package cn.kun.base.api.service.dispatch.factory;

import cn.kun.base.api.service.dispatch.RemoteLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.LogPageDTO;
import cn.kun.base.api.entity.dispatch.vo.LogPageVO;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 远程日志服务-降级处理
 *
 * @author 廖航
 * @date 2023-06-07 10:16
 */
@Slf4j
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {
    
    @Override
    public RemoteLogService create(Throwable cause) {
        
        log.error("远程日志服务：{}", cause.getMessage());
        return new RemoteLogService() {
            @Override
            public BaseResult<Page<LogPageVO>> page(LogPageDTO dto) {
                
                return BaseResult.fail();
            }
        };
    }
}
