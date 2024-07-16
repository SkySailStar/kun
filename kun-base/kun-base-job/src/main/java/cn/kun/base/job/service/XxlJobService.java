package cn.kun.base.job.service;

import cn.kun.base.api.entity.dispatch.dto.ExecutorPageDTO;
import cn.kun.base.api.entity.dispatch.dto.LogPageDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskAddDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskEditDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskNextTriggerTimeDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskPageDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskTriggerDTO;
import com.xxl.job.core.biz.model.ReturnT;

import java.util.Map;

/**
 * xxl-job 服务层
 *
 * @author 天航星
 * @date 2023-06-05 11:50
 */
public interface XxlJobService {

    /**
     * 日志-分页
     * @param dto 日志-分页-传入值
     * @return 日志列表
     */
    Map<String, Object> pageLog(LogPageDTO dto);
    
    /**
     * 执行器-分页
     *
     * @param dto 执行器-分页-传入值
     * @return 执行器列表
     */
    Map<String, Object> pageExecutor(ExecutorPageDTO dto);

    /**
     * 执行器-详情
     * @param id 执行器ID
     * @return 执行器详情
     */
    ReturnT<?> detailExecutor(Integer id);
    
    /**
     * 任务-分页
     *
     * @param dto 任务-分页-传入值
     * @return 任务列表
     */
    Map<String, Object> pageTask(TaskPageDTO dto);

    /**
     * 任务-添加
     * @param dto 任务-添加-传入值
     * @return 结果
     */
    ReturnT<?> addTask(TaskAddDTO dto);

    /**
     * 任务-修改
     * @param dto 任务-修改-传入值
     * @return 结果
     */
    ReturnT<?> editTask(TaskEditDTO dto);

    /**
     * 任务-启动
     * @param id 任务ID
     * @return 结果
     */
    ReturnT<?> startTask(Integer id);

    /**
     * 任务-停止
     * @param id 任务ID
     * @return 结果
     */
    ReturnT<?> stopTask(Integer id);

    /**
     * 任务-删除
     * @param id 任务ID
     * @return 结果
     */
    ReturnT<?> removeTask(Integer id);

    /**
     * 任务-触发
     * @param dto 任务-触发-传入值
     * @return 结果
     */
    ReturnT<?> triggerTask(TaskTriggerDTO dto);

    /**
     * 任务-后续触发时间
     * @param dto xxl-job 任务后续触发时间
     * @return 结果
     */
    ReturnT<?> nextTriggerTimeTask(TaskNextTriggerTimeDTO dto);
    
}
