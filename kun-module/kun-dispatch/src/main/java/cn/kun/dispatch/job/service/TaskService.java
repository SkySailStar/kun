package cn.kun.dispatch.job.service;

import cn.kun.base.api.entity.dispatch.dto.TaskAddDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskEditDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskNextTriggerTimeDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskPageDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskTriggerDTO;
import cn.kun.base.api.entity.dispatch.vo.TaskPageVO;
import java.util.List;

/**
 * xxl-job任务-服务层
 *
 * @author 廖航
 * @date 2023-06-01 16:58
 */
public interface TaskService {

    /**
     * 分页
     * @param dto 任务-分页-传入值
     * @return 任务-分页-返回值
     */
    Page<TaskPageVO> page(TaskPageDTO dto);
    
    /**
     * 添加
     * @param dto 任务-添加-传入值
     * @return 结果
     */
    Integer add(TaskAddDTO dto);

    /**
     * 修改
     * @param dto 任务-修改-传入值
     * @return 结果
     */
    boolean edit(TaskEditDTO dto);
    
    /**
     * 删除
     * @param id 主键
     * @return 结果
     */
    boolean remove(Integer id);

    /**
     * 启动
     * @param id 主键
     * @return 结果
     */
    boolean start(Integer id);

    /**
     * 停止
     * @param id 主键
     * @return 结果
     */
    boolean stop(Integer id);

    /**
     * 触发
     * @param dto 任务-触发-传入值
     * @return 结果
     */
    boolean trigger(TaskTriggerDTO dto);

    /**
     * 后续触发时间
     * @param dto xxl-job 任务后续触发时间-传入值
     * @return 执行时间列表
     */
    List<String> nextTriggerTime(TaskNextTriggerTimeDTO dto);
    
}
