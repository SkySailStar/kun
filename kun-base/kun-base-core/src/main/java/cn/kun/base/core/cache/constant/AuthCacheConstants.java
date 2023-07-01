package cn.kun.base.core.cache.constant;

/**
 * Redis相关常量-认证服务
 *
 * @author 廖航
 */
public class AuthCacheConstants {

    /**
     * 登录信息
     */
    public final static String LOGIN_INFO_HASH = "auth:login_info_hash";

    /**
     * 登录密码错误
     */
    public final static String LOGIN_PASSWORD_ERROR_HASH = "auth:login_password_error_hash";

    /**
     * 登录限制
     */
    public final static String LOGIN_LIMIT = "auth:login_limit_";
    
    /** 
     * 用户id
     */
    public final static String USER_ID = "auth:userId_";
    
    /** 
     * 项目
     */
    public final static String PROJECT_HASH = "auth:project_hash";

    /**
     * 内部公司
     */
    public final static String COMPANY_INNER_HASH = "auth:company_inner_hash";
    
    /**
     * 外部公司
     */
    public final static String COMPANY_OUTER_HASH = "auth:company_outer_hash";

    /** 
     * 内部公司id
     */
    public final static String COMPANY_INNER_ID = "auth:company_inner_id_";
    
    /** 
     * 外部公司id
     */
    public final static String COMPANY_OUTER_ID = "auth:company_outer_id_";
    
    /** 
     * 内部角色
     */
    public final static String ROLE_INNER_HASH = "auth:role_inner_hash";
    
    /** 
     * 外部角色
     */
    public final static String ROLE_OUTER_HASH = "auth:role_outer_hash";
    
    /** 
     * 内部用户
     */
    public final static String USER_INNER_HASH = "auth:user_inner_hash";
    
    /** 
     * 外部用户
     */
    public final static String USER_OUTER_HASH = "auth:user_outer_hash";

}