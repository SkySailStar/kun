package cn.kun.base.api.service.dispatch;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.TaskAddDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskEditDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskNextTriggerTimeDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskPageDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskTriggerDTO;
import cn.kun.base.api.entity.dispatch.vo.TaskPageVO;
import cn.kun.base.api.service.dispatch.factory.RemoteTaskFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * 远程任务服务
 *
 * @author SkySailStar
 * @date 2023-06-07 10:27
 */
@FeignClient(value = ServiceConstants.DISPATCH, fallbackFactory = RemoteTaskFallbackFactory.class)
public interface RemoteTaskService {

    /**
     * 分页
     * @param dto 任务-分页-传入值
     * @return 任务分页列表
     */
    @PostMapping("task/page")
    BaseResult<Page<TaskPageVO>> page(@RequestBody TaskPageDTO dto);

    /**
     * 添加
     * @param dto 任务-添加-传入值
     * @return 主键
     */
    @PostMapping("task")
    BaseResult<Integer> add(@RequestBody TaskAddDTO dto);

    /**
     * 修改
     * @param dto 任务-修改-传入值
     * @return 结果
     */
    @PutMapping("task")
    BaseResult<Boolean> edit(@RequestBody TaskEditDTO dto);

    /**
     * 删除
     * @param id 主键
     * @return 结果
     */
    @DeleteMapping("task/{id}")
    BaseResult<Boolean> remove(@PathVariable("id") Integer id);

    /**
     * 启动
     * @param id 主键
     * @return 结果
     */
    @PostMapping("task/start/{id}")
    BaseResult<Boolean> start(@PathVariable("id") Integer id);

    /**
     * 停止
     * @param id 主键
     * @return 结果
     */
    @PostMapping("task/stop/{id}")
    BaseResult<Boolean> stop(@PathVariable("id") Integer id);

    /**
     * 触发
     * @param dto 任务-触发-传入值
     * @return 结果
     */
    @PostMapping("task/trigger")
    BaseResult<Boolean> trigger(@RequestBody TaskTriggerDTO dto);

    /**
     * 后续触发时间
     * @param dto 任务后续触发时间-传入值
     * @return 后续触发时间列表
     */
    @PostMapping("task/next-trigger-time")
    BaseResult<List<String>> nextTriggerTime(@RequestBody TaskNextTriggerTimeDTO dto);
    
}
