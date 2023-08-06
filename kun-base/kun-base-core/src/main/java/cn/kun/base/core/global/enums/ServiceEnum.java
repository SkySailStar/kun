package cn.kun.base.core.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务-枚举类
 *
 * @author SkySailStar
 */
@Getter
@AllArgsConstructor
public enum ServiceEnum {

    /**
     * 示例服务
     */
    DEMO("kun-demo", "示例服务", 45000),
    
    /**
     * 认证服务
     */
    AUTH("kun-auth", "认证服务", 45001),

    /**
     * 系统服务
     */
    SYSTEM("kun-system", "系统服务", 45002),

    /**
     * 消息服务
     */
    MESSAGE("kun-message", "消息服务", 45003),

    /**
     * 产品服务
     */
    PRODUCT("kun-product", "产品服务", 45004),

    /**
     * 巡检服务
     */
    PATROL("kun-patrol", "巡检服务", 45005),

    /**
     * 台账服务
     */
    ASSETS("kun-assets", "台账服务", 45006),

    /**
     * 预警服务
     */
    WARN("kun-warn", "预警服务", 45007),

    /**
     * 调度服务
     */
    DISPATCH("kun-dispatch", "调度服务", 45008),

    /**
     * 策略服务
     */
    STRATEGY("kun-strategy", "策略服务", 45009),

    /**
     * 机器人算法服务
     */
    ROBOT_AI("kun-robotai", "机器人算法服务", 45010),

    /**
     * 算法服务
     */
    AI("kun-ai", "算法服务", 45011),

    /**
     * 手机应用服务
     */
    APP("kun-app", "手机应用服务", 45012);
    
    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 端口
     */
    private final int port;

    /**
     * 根据编码获取描述
     * @param code 编码
     * @return 描述
     */
    public static String getDescByCode(String code) {
        for (ServiceEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value.getDesc();
            }
        }
        return null;
    }


}