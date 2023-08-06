package cn.kun.base.core.job.constants;

/**
 * xxl-job请求路径-常量类
 *
 * @author SkySailStar
 * @date 2023-06-01 15:44
 */
public class XxlJobTaskPathConstants {

    /**
     * 登录
     */
    public static final String LOGIN = "/login";

    /**
     * 日志-分页
     */
    public static final String LOG_PAGE = "/joblog/pageList";

    /**
     * 执行器-分页
     */
    public static final String EXECUTOR_PAGE = "/jobgroup/pageList";

    /**
     * 执行器-详情
     */
    public static final String EXECUTOR_DETAIL = "/jobgroup/loadById";
    
    /**
     * 任务-分页
     */
    public static final String TASK_PAGE = "/jobinfo/pageList";

    /**
     * 任务-分页添加
     */
    public static final String TASK_ADD = "/jobinfo/add";

    /**
     * 任务-分页修改
     */
    public static final String TASK_EDIT = "/jobinfo/update";

    /**
     * 任务-分页启动
     */
    public static final String TASK_START = "/jobinfo/start";

    /**
     * 任务-分页停止
     */
    public static final String TASK_STOP = "/jobinfo/stop";

    /**
     * 任务-分页删除
     */
    public static final String TASK_REMOVE = "/jobinfo/remove";

    /**
     * 任务-分页触发
     */
    public static final String TASK_TRIGGER = "/jobinfo/trigger";
    
    /**
     * 任务-分页后续触发时间
     */
    public static final String TASK_NEXT_TRIGGER_TIME = "/jobinfo/nextTriggerTime";

}
