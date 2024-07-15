package cn.kun.base.job.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.kun.base.core.global.util.coll.CollUtils;
import cn.kun.base.core.global.util.date.LocalDateTimeUtils;
import cn.kun.base.core.job.constants.XxlJobLoginConstants;
import cn.kun.base.core.job.constants.XxlJobTaskPathConstants;
import cn.kun.base.api.entity.dispatch.dto.ExecutorPageDTO;
import cn.kun.base.api.entity.dispatch.dto.LogPageDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskAddDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskEditDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskNextTriggerTimeDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskPageDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskTriggerDTO;
import cn.kun.base.job.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * xxl-job 服务层实现类
 *
 * @author 天航星
 * @date 2023-06-01 16:29
 */
@SuppressWarnings("all")
@Component
public class XxlJobServiceImpl implements XxlJobService {

    /**
     * 登录后获取的Cookie，用于请求调度中心接口
     */
    private static String cookie = "";

    /**
     * 调度中心请求地址
     */
    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;
    
    /**
     * 请求模板
     */
    @Resource
    private RestTemplate restTemplate;
    
    /**
     * 调度器登录
     */
    @PostConstruct
    public void login() {
        
        // 请求网址
        String url = adminAddresses + XxlJobTaskPathConstants.LOGIN;
        /* 请求体 */
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("userName", XxlJobLoginConstants.ADMIN_USERNAME);
        body.add("password", XxlJobLoginConstants.ADMIN_PASSWORD);
        body.add("ifRemember", XxlJobLoginConstants.REMEMBER_PASSWORD);
        
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = new HttpEntity<>(body, null);
        // 发送请求
        ResponseEntity<ReturnT> entity = restTemplate.postForEntity(url, param, ReturnT.class);
        // 获取cookie
        List<String> cookies = entity.getHeaders().get("Set-Cookie");
        if (CollUtils.isEmpty(cookies)) {
            return;
        }
        // 设置cookie
        StringBuilder tmpCookie = new StringBuilder();
        for (String cookie : cookies) {
            tmpCookie.append(cookie).append(";");
        }
        cookie = tmpCookie.toString();
    }

    @Override
    public Map<String, Object> pageLog(LogPageDTO dto) {

        // 请求网址
        String url = adminAddresses + XxlJobTaskPathConstants.LOG_PAGE;
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = buildRequestParam(dto);
        // 发送请求
        return restTemplate.postForObject(url, param, Map.class);
    }

    @Override
    public Map<String, Object> pageExecutor(ExecutorPageDTO dto) {

        // 请求网址
        String url = adminAddresses + XxlJobTaskPathConstants.EXECUTOR_PAGE;
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = buildRequestParam(dto);
        // 发送请求
        return restTemplate.postForObject(url, param, Map.class);
    }

    @Override
    public ReturnT detailExecutor(Integer id) {

        // 请求网址
        String url = adminAddresses + XxlJobTaskPathConstants.EXECUTOR_DETAIL;
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = buildRequestParam(id);
        // 发送请求
        return restTemplate.postForObject(url, param, ReturnT.class);
    }

    @Override
    public Map<String, Object> pageTask(TaskPageDTO dto) {

        // 请求网址
        String url = adminAddresses + XxlJobTaskPathConstants.TASK_PAGE;
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = buildRequestParam(dto);
        // 发送请求
        return restTemplate.postForObject(url, param, Map.class);
    }

    public ReturnT addTask(TaskAddDTO dto) {
        
        // 请求网址
        String url = adminAddresses + XxlJobTaskPathConstants.TASK_ADD;
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = buildRequestParam(dto);
        // 发送请求
        return restTemplate.postForObject(url, param, ReturnT.class);
    }
    
    public ReturnT editTask(TaskEditDTO dto) {

        // 请求网址
        String url = adminAddresses + XxlJobTaskPathConstants.TASK_EDIT;
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = buildRequestParam(dto);
        return restTemplate.postForObject(url, param, ReturnT.class);
    }
    
    public ReturnT startTask(Integer id) {

        // 请求网址
        String url = adminAddresses + XxlJobTaskPathConstants.TASK_START;
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = buildRequestParam(id);
        return restTemplate.postForObject(url, param, ReturnT.class);
    }
    
    public ReturnT stopTask(Integer id) {
        
        // 请求网址
        String url =  adminAddresses + XxlJobTaskPathConstants.TASK_STOP;
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = buildRequestParam(id);
        return restTemplate.postForObject(url, param, ReturnT.class);
    }
    
    public ReturnT removeTask(Integer id) {

        // 请求网址
        String url =  adminAddresses + XxlJobTaskPathConstants.TASK_REMOVE;
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = buildRequestParam(id);
        return restTemplate.postForObject(url, param, ReturnT.class);
    }
    
    public ReturnT triggerTask(TaskTriggerDTO dto) {

        // 请求网址
        String url =  adminAddresses + XxlJobTaskPathConstants.TASK_TRIGGER;
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = buildRequestParam(dto);
        return restTemplate.postForObject(url, param, ReturnT.class);
    }
    
    public ReturnT nextTriggerTimeTask(TaskNextTriggerTimeDTO dto) {

        // 请求网址
        String url =  adminAddresses + XxlJobTaskPathConstants.TASK_NEXT_TRIGGER_TIME;
        // 请求参数
        HttpEntity<MultiValueMap<String, Object>> param = buildRequestParam(dto);
        return restTemplate.postForObject(url, param, ReturnT.class);
    }

    /**
     * 构建请求头
     * @return 请求头
     */
    private MultiValueMap<String, String> buildRequestHeaders() {
        
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("cookie", cookie);
        return headers;
    }

    /**
     * 构建请求参数
     * @param dto 任务-添加-传入值
     * @return 请求参数
     */
    private HttpEntity<MultiValueMap<String, Object>> buildRequestParam(TaskAddDTO dto) {
        
        /* 请求体 */
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        /* 基础配置 */
        // 执行器ID
        body.add("jobGroup", dto.getExecutorId());
        // 任务描述
        body.add("jobDesc", dto.getTaskDesc());
        // 负责人
        body.add("author", dto.getAuthor());
        // 报警邮件
        body.add("alarmEmail", dto.getAlarmEmail());

        /* 调度配置 */
        // 调度类型
        body.add("scheduleType", dto.getScheduleType());
        // 调度配置，值含义取决于调度类型
        body.add("scheduleConf", dto.getScheduleConf());

        /* 任务配置 */
        // 运行模式	#com.xxl.job.core.glue.GlueTypeEnum
        body.add("glueType", dto.getGlueType());
        // 执行器，任务Handler名称
        body.add("executorHandler", dto.getExecutorHandler());
        // 执行器，任务参数
        body.add("executorParam", dto.getExecutorParam());
        // GLUE源代码
        body.add("glueSource", dto.getGlueSource());
        // GLUE备注
        body.add("glueRemark", dto.getGlueRemark());

        /* 高级配置 */
        // 执行器路由策略
        body.add("executorRouteStrategy", dto.getExecutorRouteStrategy());
        // 子任务ID，多个逗号分隔
        body.add("childJobId", dto.getChildJobId());
        // 调度过期策略
        body.add("misfireStrategy", dto.getMisfireStrategy());
        // 阻塞处理策略
        body.add("executorBlockStrategy", dto.getExecutorBlockStrategy());
        // 任务执行超时时间，单位秒
        body.add("executorTimeout", dto.getExecutorTimeout());
        // 失败重试次数
        body.add("executorFailRetryCount", dto.getExecutorFailRetryCount());
        // 请求参数
        return new HttpEntity<>(body, buildRequestHeaders());
    }

    /**
     * 构建请求参数
     * @param dto 任务-传入值
     * @return 请求参数
     */
    private HttpEntity<MultiValueMap<String, Object>> buildRequestParam(TaskEditDTO dto) {

        /* 请求体 */
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // 主键
        body.add("id", dto.getId());
        /* 基础配置 */
        // 执行器ID
        body.add("jobGroup", dto.getExecutorId());
        // 任务描述
        body.add("jobDesc", dto.getTaskDesc());
        // 负责人
        body.add("author", dto.getAuthor());
        // 报警邮件
        body.add("alarmEmail", dto.getAlarmEmail());

        /* 调度配置 */
        // 调度类型
        body.add("scheduleType", dto.getScheduleType());
        // 调度配置，值含义取决于调度类型
        body.add("scheduleConf", dto.getScheduleConf());
        
        /* 高级配置 */
        // 执行器路由策略
        body.add("executorRouteStrategy", dto.getExecutorRouteStrategy());
        // 子任务ID，多个逗号分隔
        body.add("childJobId", dto.getChildJobId());
        // 调度过期策略
        body.add("misfireStrategy", dto.getMisfireStrategy());
        // 阻塞处理策略
        body.add("executorBlockStrategy", dto.getExecutorBlockStrategy());
        // 任务执行超时时间，单位秒
        body.add("executorTimeout", dto.getExecutorTimeout());
        // 失败重试次数
        body.add("executorFailRetryCount", dto.getExecutorFailRetryCount());
        // 请求参数
        return new HttpEntity<>(body, buildRequestHeaders());
    }

    /**
     * 构建请求参数
     * @param id 主键
     * @return 请求参数
     */
    private HttpEntity<MultiValueMap<String, Object>> buildRequestParam(Integer id) {

        /* 请求体 */
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // 主键
        body.add("id", id);
        
        // 请求参数
        return new HttpEntity<>(body, buildRequestHeaders());
    }

    /**
     * 构建请求参数
     * @param dto 任务-触发-传入值
     * @return 请求参数
     */
    private HttpEntity<MultiValueMap<String, Object>> buildRequestParam(TaskTriggerDTO dto) {

        /* 请求体 */
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // 主键
        body.add("id", dto.getId());
        // 执行参数
        body.add("executorParam", dto.getExecutorParam());
        // 地址列表
        body.add("addressList", dto.getAddressList());
        // 请求参数
        return new HttpEntity<>(body, buildRequestHeaders());
    }

    /**
     * 构建请求参数
     * @param dto xxl-job 任务后续触发时间-传入值
     * @return 请求参数
     */
    private HttpEntity<MultiValueMap<String, Object>> buildRequestParam(TaskNextTriggerTimeDTO dto) {

        /* 请求体 */
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // 定时类型
        body.add("scheduleType", dto.getScheduleType());
        // 定时配置
        body.add("scheduleConf", dto.getScheduleConf());
        // 请求参数
        return new HttpEntity<>(body, buildRequestHeaders());
    }

    /**
     * 构建请求参数
     * @param dto 任务-分页-传入值
     * @return 请求参数
     */
    private HttpEntity<MultiValueMap<String, Object>> buildRequestParam(TaskPageDTO dto) {

        /* 请求体 */
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // 记录起始位置
        body.add("start", buildStart(dto.getPageNo(), dto.getPageSize()));
        // 记录条数
        body.add("length", dto.getPageSize());
        // 执行器ID
        body.add("jobGroup", dto.getExecutorId());
        // 调度状态
        body.add("triggerStatus", dto.getTriggerStatus());
        // 任务描述
        body.add("jobDesc", dto.getTaskDesc());
        // 执行器，任务Handler名称
        body.add("executorHandler", dto.getExecutorHandler());
        // 负责人
        body.add("author", dto.getAuthor());
        // 请求参数
        return new HttpEntity<>(body, buildRequestHeaders());
    }

    /**
     * 构建请求参数
     * @param dto xxl-job 执行器-分页-传入值
     * @return 请求参数
     */
    private HttpEntity<MultiValueMap<String, Object>> buildRequestParam(ExecutorPageDTO dto) {

        /* 请求体 */
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // 记录起始位置
        body.add("start", buildStart(dto.getPageNo(), dto.getPageSize()));
        // 记录条数
        body.add("length", dto.getPageSize());
        // 应用编码
        body.add("appname", dto.getAppCode());
        // 应用名称
        body.add("title", dto.getAppName());
        // 请求参数
        return new HttpEntity<>(body, buildRequestHeaders());
    }

    /**
     * 构建请求参数
     * @param dto 日志-分页-传入值
     * @return 请求参数
     */
    private HttpEntity<MultiValueMap<String, Object>> buildRequestParam(LogPageDTO dto) {

        /* 请求体 */
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // 记录起始位置
        body.add("start", buildStart(dto.getPageNo(), dto.getPageSize()));
        // 记录条数
        body.add("length", dto.getPageSize());
        // 执行器ID
        body.add("jobGroup", dto.getExecutorId());
        // 任务ID
        body.add("jobId", dto.getTaskId());
        // 日志状态
        body.add("logStatus", dto.getLogStatus());
        // 执行开始结束时间
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        String startTime = LocalDateTimeUtils.format(dto.getStartTime(), dateTimeFormatter);
        String endTime = LocalDateTimeUtils.format(dto.getEndTime(), dateTimeFormatter);
        body.add("filterTime", startTime + " - " + endTime);
        // 请求参数
        return new HttpEntity<>(body, buildRequestHeaders());
    }

    /**
     * 构建起始记录
     * @param pageNo 页码
     * @param pageSize 条数
     * @return 起始记录
     */
    private int buildStart(int pageNo, int pageSize) {
        
        return (pageNo - 1) * pageSize;
    }
    
}
