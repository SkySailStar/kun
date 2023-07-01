package cn.kun.base.api.service.dispatch.factory;

import cn.kun.base.api.service.dispatch.RemoteTaskService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.TaskAddDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskEditDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskNextTriggerTimeDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskPageDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskTriggerDTO;
import cn.kun.base.api.entity.dispatch.vo.TaskPageVO;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 远程任务服务-降级处理
 *
 * @author 廖航
 * @date 2023-06-07 10:27
 */
@Slf4j
@Component
public class RemoteTaskFallbackFactory implements FallbackFactory<RemoteTaskService> {
    
    @Override
    public RemoteTaskService create(Throwable cause) {
        
        log.error("远程任务服务：{}", cause.getMessage());
        return new RemoteTaskService() {
            
            @Override
            public BaseResult<Page<TaskPageVO>> page(TaskPageDTO dto) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<Integer> add(TaskAddDTO dto) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<Boolean> edit(TaskEditDTO dto) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<Boolean> remove(Integer id) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<Boolean> start(Integer id) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<Boolean> stop(Integer id) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<Boolean> trigger(TaskTriggerDTO dto) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<String>> nextTriggerTime(TaskNextTriggerTimeDTO dto) {
                
                return BaseResult.fail();
            }
        };
    }
}
