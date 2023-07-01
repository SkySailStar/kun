package cn.kun.base.core.global.constant;

/**
 * 手机应用服务-常量类
 *
 * @author 廖航
 * @date 2023-06-13 14:10
 */
public class AppConstants {

    /**
     * 气象站设备编码
     */
    public static final String WEATHER_STATION_COMPOSE_CODE = "WEATHER_STATION";
    
    /**
     * 华为天气接口请求头
     */
    public static final String HUAWEI_WEATHER_HEADER = "X-Apig-AppCode";

    /**
     * 气象站数据心跳有效时间（单位：分钟）
     */
    public static final int WEATHER_STATION_HEART_TIME = 10;

    /**
     * 设备信息心跳有效时间（单位：分钟）
     */
    public static final int DEVICE_INFO_HEART_TIME = 5;
    
}
