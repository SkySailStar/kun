package cn.kun.base.core.mq.constant;

/**
 * 消息队列
 *
 * @author SkySailStar
 * @date 2023-04-19 14:50
 */
public class QueueConstants {
    
    /**
     * 直连模式-系统服务-错误信息
     */
    public static final String DIRECT_SYSTEM_ERROR_INFO = "direct.system.error_info";

    /**
     * 直连模式-机器人算法服务-原始数据
     */
    public static final String DIRECT_ROBOTAI_SOURCE_DATA = "direct.robotai.source_data";
    
    /**
     * 发布订阅模式-机器人算法服务-结果数据-AI平台
     */
    public static final String SUBSCRIBE_ROBOTAI_RESULT_DATA_AI = "subscribe.robotai.result_data_ai";

    /**
     * 发布订阅模式-机器人算法服务-结果数据-巡检服务
     */
    public static final String SUBSCRIBE_ROBOTAI_RESULT_DATA_PATROL = "subscribe.robotai.result_data_patrol";

}
