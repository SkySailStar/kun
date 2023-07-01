package cn.kun.base.core.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 预警等级-枚举类
 *
 * @author 廖航
 */
@Getter
@AllArgsConstructor
public enum WarnLevelEnum {

    /**
     * 一级预警
     */
    FIRST_WARN("FIRST_WARN", "一级预警"),

    /**
     * 二级预警
     */
    SECOND_WARN("SECOND_WARN", "二级预警"),

    /**
     * 三级预警
     */
    THIRD_WARN("THIRD_WARN", "三级预警");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
    
    /**
     * 根据编码获取描述
     * @param code 编码
     * @return 描述
     */
    public static String getDescByCode(String code) {
        for (WarnLevelEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value.getDesc();
            }
        }
        return null;
    }
    
}