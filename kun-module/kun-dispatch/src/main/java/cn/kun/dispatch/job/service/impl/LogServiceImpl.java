package cn.kun.dispatch.job.service.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.service.system.DictService;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.HttpStatusConstants;
import cn.kun.base.core.global.constant.dict.type.DispatchDictTypeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.coll.CollUtils;
import cn.kun.base.core.global.util.obj.ObjUtils;
import cn.kun.base.core.global.util.str.StrUtils;
import cn.kun.base.api.entity.dispatch.dto.ExecutorPageDTO;
import cn.kun.base.api.entity.dispatch.dto.LogPageDTO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorDetailVO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorPageVO;
import cn.kun.base.api.entity.dispatch.vo.LogPageVO;
import cn.kun.base.job.service.XxlJobService;
import cn.kun.dispatch.job.service.ExecutorService;
import cn.kun.dispatch.job.service.LogService;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 日志-服务层实现类
 *
 * @author 廖航
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Value("${xxl.job.executor.appname}")
    private String appname;
    
    @Resource
    private XxlJobService xxlJobService;
    
    @Resource
    private ExecutorService executorService;
    
    @Resource
    private DictService baseDictService;
    
    @Override
    public Page<LogPageVO> page(LogPageDTO dto) {
        
        log.info("日志-分页：{}", dto);
        // 默认数据
        buildDefault(dto);
        Map<String, Object> resultMap = xxlJobService.pageLog(dto);
        // 失败情况
        if (StrUtils.equals(Convert.toStr(resultMap.get("code")), HttpStatusConstants.ERROR)) {
            log.warn("日志-分页-失败：{}", Convert.toStr(resultMap.get("msg")));
            throw new BusinessException(ErrorCodeConstants.QUERY_FAIL, "日志-分页-失败：" + Convert.toStr(resultMap.get("msg")));
        }
        // 定义返回值
        Page<LogPageVO> voPage = Page.of(dto.getPageNo(), dto.getPageSize());
        voPage.setTotal(Convert.toInt(resultMap.get("recordsTotal")));
        if (resultMap.get("data") instanceof List<?> list) {
            voPage.setRecords(list.stream().map(this::cast).toList());
            return voPage;
        }
        return null;
    }

    /**
     * 构建默认值
     * @param dto 任务-分页-传入值
     */
    private void buildDefault(LogPageDTO dto) {

        // 执行器ID
        if (ObjUtils.isNull(dto.getExecutorId())) {
            dto.setExecutorId(buildExecutorId());
        }
        // 任务ID
        if (ObjUtils.isNull(dto.getTaskId())) {
            dto.setTaskId(0);
        }
        // 日志状态
        if (ObjUtils.isNull(dto.getLogStatus())) {
            dto.setLogStatus(-1);
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

    /**
     * 转换
     * @param obj 对象
     * @return 日志-分页-返回值
     */
    private LogPageVO cast(Object obj) {

        JSONObject json = JSON.parseObject(JSON.toJSONString(obj));
        LogPageVO vo = new LogPageVO();
        // 主键
        vo.setId(Convert.toLong(json.get("id")));
        // 执行器ID
        Integer executorId = Convert.toInt(json.get("jobGroup"));
        vo.setExecutorId(executorId);
        // 执行器名称
        ExecutorDetailVO executorDetailVO = executorService.detail(executorId);
        if (ObjUtils.isNotNull(executorDetailVO)) {
            vo.setExecutorName(executorDetailVO.getAppName());
        }
        // 任务ID
        vo.setTaskId(Convert.toInt(json.get("jobId")));
        // 调度时间
        vo.setTriggerTime(Convert.toLocalDateTime(json.get("triggerTime")));
        // 调度结果编码
        vo.setTriggerCode(Convert.toInt(json.get("triggerCode")));
        // 调度详情
        vo.setTriggerMsg(Convert.toStr(json.get("triggerMsg")));
        // 执行时间
        vo.setExecutorTime(Convert.toLocalDateTime(json.get("handleTime")));
        // 执行结果编码
        vo.setExecutorCode(Convert.toInt(json.get("handleCode")));
        // 执行结果消息
        vo.setExecutorMsg(Convert.toStr(json.get("handleMsg")));
        // 告警状态
        Integer alarmStatus = Convert.toInt(json.get("alarmStatus"));
        vo.setAlarmStatus(alarmStatus);
        // 告警状态名称
        vo.setAlarmStatusName(baseDictService.getLabel(DispatchDictTypeConstants.ALARM_STATUS, Convert.toStr(alarmStatus)));
        return vo;
    }
}