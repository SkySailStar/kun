package cn.kun.dispatch.job.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.ExecutorPageDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskAddDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskEditDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskNextTriggerTimeDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskPageDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskTriggerDTO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorDetailVO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorPageVO;
import cn.kun.base.api.entity.dispatch.vo.TaskPageVO;
import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.HttpStatusConstants;
import cn.kun.base.core.global.constant.dict.type.DispatchDictTypeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.coll.CollUtils;
import cn.kun.base.core.global.util.obj.ObjUtils;
import cn.kun.base.core.global.util.str.StrUtils;
import cn.kun.base.core.job.constants.XxlJobDefaultConstants;
import cn.kun.base.job.service.XxlJobService;
import cn.kun.dispatch.job.service.ExecutorService;
import cn.kun.dispatch.job.service.TaskService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.glue.GlueTypeEnum;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * xxl-job任务-服务层实现类
 *
 * @author 廖航
 * @date 2023-06-01 17:36
 */
@SuppressWarnings("all")
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    
    @Value("${xxl.job.executor.appname}")
    private String appname;
    
    @Resource
    private XxlJobService xxlJobService;
    
    @Resource
    private ExecutorService executorService;
    
    @Resource
    private BaseDictService baseDictService;

    @Override
    public Page<TaskPageVO> page(TaskPageDTO dto) {
        
        log.info("定时任务-分页：{}", dto);
        // 默认数据
        buildDefault(dto);
        // 分页数据
        Map<String, Object> resultMap = xxlJobService.pageTask(dto);
        // 失败情况
        if (StrUtils.equals(Convert.toStr(resultMap.get("code")), HttpStatusConstants.ERROR)) {
            log.warn("定时任务-分页-失败：{}", Convert.toStr(resultMap.get("msg")));
            throw new BusinessException(ErrorCodeConstants.QUERY_FAIL, "定时任务-分页-失败：" + Convert.toStr(resultMap.get("msg")));
        }
        // 定义返回值
        Page<TaskPageVO> voPage = Page.of(dto.getPageNo(), dto.getPageSize());
        voPage.setTotal(Convert.toInt(resultMap.get("recordsTotal")));
        if (resultMap.get("data") instanceof List list) {
            voPage.setRecords(list.stream().map(this::cast).toList());
            return voPage;
        }
        return null;
    }

    @Override
    public Integer add(TaskAddDTO dto) {

        log.info("定时任务-添加：{}", dto);
        // 参数检查
        check(dto);
        // 设置默认值
        buildDefault(dto);
        // 添加任务
        ReturnT returnT = xxlJobService.addTask(dto);
        if (ObjUtil.isNull(returnT) || returnT.getCode() == ReturnT.FAIL_CODE) {
            log.warn("定时任务-添加-失败：{}", returnT.getMsg());
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL, "定时任务-添加-失败：" + returnT.getMsg());
        }
        if (returnT.getContent() instanceof String id) {
            return Convert.toInt(id);
        }
        return null;
    }

    @Override
    public boolean edit(TaskEditDTO dto) {

        log.info("定时任务-修改：{}", dto);
        // 设置默认值
        buildDefault(dto);
        // 修改任务
        ReturnT returnT = xxlJobService.editTask(dto);
        if (ObjUtil.isNull(returnT) || returnT.getCode() == ReturnT.FAIL_CODE) {
            log.warn("定时任务-修改-失败：{}", returnT.getMsg());
            throw new BusinessException(ErrorCodeConstants.EDIT_FAIL, "定时任务-修改-失败：" + returnT.getMsg());
        }
        return true;
    }
    
    @Override
    public boolean remove(Integer id) {

        log.info("定时任务-删除：{}", id);
        // 删除任务
        ReturnT returnT = xxlJobService.removeTask(id);
        if (ObjUtil.isNull(returnT) || returnT.getCode() == ReturnT.FAIL_CODE) {
            log.warn("定时任务-删除-失败：{}", returnT.getMsg());
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "定时任务-删除-失败：" + returnT.getMsg());
        }
        return true;
    }

    @Override
    public boolean start(Integer id) {

        log.info("定时任务-启动：{}", id);
        // 启动任务
        ReturnT returnT = xxlJobService.startTask(id);
        if (ObjUtil.isNull(returnT) || returnT.getCode() == ReturnT.FAIL_CODE) {
            log.warn("定时任务-启动-失败：{}", returnT.getMsg());
            throw new BusinessException(ErrorCodeConstants.OPERATE_FAIL, "定时任务-启动-失败" + returnT.getMsg());
        }
        return true;
    }

    @Override
    public boolean stop(Integer id) {

        log.info("定时任务-停止：{}", id);
        // 停止任务
        ReturnT returnT = xxlJobService.stopTask(id);
        if (ObjUtil.isNull(returnT) || returnT.getCode() == ReturnT.FAIL_CODE) {
            log.warn("定时任务-停止-失败：{}", returnT.getMsg());
            throw new BusinessException(ErrorCodeConstants.OPERATE_FAIL, "定时任务-停止-失败：" + returnT.getMsg());
        }
        return true;
    }

    @Override
    public boolean trigger(TaskTriggerDTO dto) {

        log.info("定时任务-触发：{}", dto);
        // 触发任务
        ReturnT returnT = xxlJobService.triggerTask(dto);
        if (ObjUtil.isNull(returnT) || returnT.getCode() == ReturnT.FAIL_CODE) {
            log.warn("定时任务-触发-失败：{}", returnT.getMsg());
            throw new BusinessException(ErrorCodeConstants.OPERATE_FAIL, "定时任务-触发-失败：" + returnT.getMsg());
        }
        return true;
    }

    @Override
    public List<String> nextTriggerTime(TaskNextTriggerTimeDTO dto) {

        log.info("定时任务-后续触发时间：{}", dto);
        // 后续触发时间
        ReturnT returnT = xxlJobService.nextTriggerTimeTask(dto);
        if (ObjUtil.isNull(returnT) || returnT.getCode() == ReturnT.FAIL_CODE) {
            log.warn("定时任务-后续触发时间-失败：{}", returnT.getMsg());
            throw new BusinessException(ErrorCodeConstants.QUERY_FAIL, "定时任务-后续触发时间-失败：" + returnT.getMsg());
        }
        if (returnT.getContent() instanceof List list) {
            return list;
        }
        return null;
    }

    /**
     * 检查
     * @param dto 任务-添加-传入值
     */
    private void check(TaskAddDTO dto) {
        
        // 如果选择的是BEAN类型，则执行器处理器不能为空
        if (StrUtils.equals(dto.getGlueType(), GlueTypeEnum.BEAN.getDesc()) && StrUtils.isBlank(dto.getExecutorHandler())) {
            throw new BusinessException(ErrorCodeConstants.NULL, "执行器处理器：不能为空");
        }
    }

    /**
     * 转换
     * @param obj 对象
     * @return 任务-分页-返回值
     */
    private TaskPageVO cast(Object obj) {

        JSONObject json = JSON.parseObject(JSON.toJSONString(obj));
        TaskPageVO vo = new TaskPageVO();
        // 主键
        vo.setId(Convert.toInt(json.get("id")));
        // 执行器ID
        Integer executorId = Convert.toInt(json.get("jobGroup"));
        vo.setExecutorId(executorId);
        // 执行器名称
        ExecutorDetailVO executorDetailVO = executorService.detail(executorId);
        if (ObjUtils.isNotNull(executorDetailVO)) {
            vo.setExecutorName(executorDetailVO.getAppName());
        }
        // 任务描述
        vo.setTaskDesc(Convert.toStr(json.get("jobDesc")));
        // 负责人
        vo.setAuthor(Convert.toStr(json.get("author")));
        // 调度类型
        vo.setScheduleType(Convert.toStr(json.get("scheduleType")));
        // 运行模式
        vo.setGlueType(Convert.toStr(json.get("glueType")));
        // 调度状态
        String triggerStatus = Convert.toStr(json.get("triggerStatus"));
        vo.setTriggerStatus(triggerStatus);
        // 调度状态名称
        vo.setTriggerStatusName(baseDictService.getLabel(DispatchDictTypeConstants.TRIGGER_STATUS, triggerStatus));
        // 更新时间
        vo.setUpdateTime(Convert.toLocalDateTime(json.get("updateTime")));
        return vo;
    }

    /**
     * 构建默认值
     * @param dto 任务-添加-传入值
     */
    private void buildDefault(TaskAddDTO dto) {

        // 执行器ID
        if (ObjUtils.isNull(dto.getExecutorId())) {
            dto.setExecutorId(buildExecutorId());
        }
        // 执行器路由策略
        if (StrUtils.isBlank(dto.getExecutorRouteStrategy())) {
            dto.setExecutorRouteStrategy(XxlJobDefaultConstants.EXECUTOR_ROUTE_STRATEGY);
        }
        // 调度过期策略
        if (StrUtils.isBlank(dto.getMisfireStrategy())) {
            dto.setMisfireStrategy(XxlJobDefaultConstants.MISFIRE_STRATEGY);
        }
        // 阻塞处理策略
        if (StrUtils.isBlank(dto.getExecutorBlockStrategy())) {
            dto.setExecutorBlockStrategy(XxlJobDefaultConstants.EXECUTOR_BLOCK_STRATEGY);
        }
        // 任务执行超时时间
        if (ObjUtils.isNull(dto.getExecutorTimeout())) {
            dto.setExecutorTimeout(XxlJobDefaultConstants.EXECUTOR_TIMEOUT);
        }
        // 失败重试次数
        if (ObjUtils.isNull(dto.getExecutorFailRetryCount())) {
            dto.setExecutorFailRetryCount(XxlJobDefaultConstants.EXECUTOR_FAIL_RETRY_COUNT);
        }
        // GLUE备注
        if (StrUtils.isBlank(dto.getGlueRemark())) {
            dto.setGlueRemark(XxlJobDefaultConstants.GLUE_REMARK);
        }
        // GLUE_GROOVY
        if (StrUtils.equals(dto.getGlueType(), GlueTypeEnum.GLUE_GROOVY.name())) {
            dto.setGlueSource(XxlJobDefaultConstants.GLUE_JAVA_SOURCE);
        }
        // GLUE_SHELL
        if (StrUtils.equals(dto.getGlueType(), GlueTypeEnum.GLUE_SHELL.name())) {
            dto.setGlueSource(XxlJobDefaultConstants.GLUE_SHELL_SOURCE);
        }
        // GLUE_PYTHON
        if (StrUtils.equals(dto.getGlueType(), GlueTypeEnum.GLUE_PYTHON.name())) {
            dto.setGlueSource(XxlJobDefaultConstants.GLUE_PYTHON_SOURCE);
        }
        // GLUE_PHP
        if (StrUtils.equals(dto.getGlueType(), GlueTypeEnum.GLUE_PHP.name())) {
            dto.setGlueSource(XxlJobDefaultConstants.GLUE_PHP_SOURCE);
        }
        // GLUE_NODEJS
        if (StrUtils.equals(dto.getGlueType(), GlueTypeEnum.GLUE_NODEJS.name())) {
            dto.setGlueSource(XxlJobDefaultConstants.GLUE_NODEJS_SOURCE);
        }
        // GLUE_POWERSHELL
        if (StrUtils.equals(dto.getGlueType(), GlueTypeEnum.GLUE_POWERSHELL.name())) {
            dto.setGlueSource(XxlJobDefaultConstants.GLUE_POWERSHELL_SOURCE);
        }
    }

    /**
     * 构建默认值
     * @param dto 任务-修改-传入值
     */
    private void buildDefault(TaskEditDTO dto) {

        // 执行器ID
        if (ObjUtils.isNull(dto.getExecutorId())) {
            dto.setExecutorId(buildExecutorId());
        }
        // 执行器路由策略
        if (StrUtils.isBlank(dto.getExecutorRouteStrategy())) {
            dto.setExecutorRouteStrategy(XxlJobDefaultConstants.EXECUTOR_ROUTE_STRATEGY);
        }
        // 调度过期策略
        if (StrUtils.isBlank(dto.getMisfireStrategy())) {
            dto.setMisfireStrategy(XxlJobDefaultConstants.MISFIRE_STRATEGY);
        }
        // 阻塞处理策略
        if (StrUtils.isBlank(dto.getExecutorBlockStrategy())) {
            dto.setExecutorBlockStrategy(XxlJobDefaultConstants.EXECUTOR_BLOCK_STRATEGY);
        }
        // 任务执行超时时间
        if (ObjUtils.isNull(dto.getExecutorTimeout())) {
            dto.setExecutorTimeout(XxlJobDefaultConstants.EXECUTOR_TIMEOUT);
        }
        // 失败重试次数
        if (ObjUtils.isNull(dto.getExecutorFailRetryCount())) {
            dto.setExecutorFailRetryCount(XxlJobDefaultConstants.EXECUTOR_FAIL_RETRY_COUNT);
        }
    }

    /**
     * 构建默认值
     * @param dto 任务-分页-传入值
     */
    private void buildDefault(TaskPageDTO dto) {

        // 执行器ID
        if (ObjUtils.isEmpty(dto.getExecutorId())) {
            dto.setExecutorId(buildExecutorId());
        }
        // 调度状态
        if (ObjUtils.isEmpty(dto.getTriggerStatus())) {
            dto.setTriggerStatus(0);
        }
    }

    /**
     * 构建执行器ID
     * @return 执行器ID
     */
    private Integer buildExecutorId() {
        
        // 执行器分页参数
        ExecutorPageDTO executorPageDTO = new ExecutorPageDTO();
        executorPageDTO.setAppCode(appname);
        // 执行器分页查询
        Page<ExecutorPageVO> page = executorService.page(executorPageDTO);
        // 获取执行器ID
        if (ObjUtils.isNotNull(page)) {
            List<ExecutorPageVO> records = page.getRecords();
            if (CollUtils.isNotEmpty(records)) {
                ExecutorPageVO executorPageVO = records.get(0);
                if (ObjUtils.isNotNull(executorPageVO)) {
                    return executorPageVO.getId();
                }
            }
        }
        return null;
    }
    
}
