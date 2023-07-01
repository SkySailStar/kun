package cn.kun.base.core.global.constant;

/**
 * 认证常量
 * @author eric
 * @date 2023/3/22 18:04
 */
public class AuthConstants {


    /**
     * 超级管理员
     */
    public final static String SYS_ADMIN = "sysadmin";

    /**
     * 用户状态-正常
     */
    public static final String USER_STATUS_NORMAL = "normal";

    /**
     * 用户状态-注销
     */
    public static final String USER_STATUS_CANCELLATION = "cancellation";

    /**
     * 用户状态-黑名单
     */
    public static final String USER_STATUS_BLACKLIST = "blacklist";

    /**
     * 登录密码错误限制次数
     */
    public static final int LOGIN_PASSWORD_ERROR_COUNT = 5;

    /**
     * 登录密码错误验证时间（单位：分钟）
     */
    public static final int LOGIN_PASSWORD_ERROR_TIME = 10;

    /**
     * 登录密码错误限制登录时间（单位：分钟）
     */
    public static final int LOGIN_LIMIT_TIME = 10;

}