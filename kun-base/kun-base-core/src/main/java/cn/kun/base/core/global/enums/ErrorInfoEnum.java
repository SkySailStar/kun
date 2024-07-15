package cn.kun.base.core.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误信息
 *
 * @author 天航星
 */
@Getter
@AllArgsConstructor
public enum ErrorInfoEnum {

    /**
     * 空指针
     */
    NULL_POINTER("null_pointer", "空指针"),
    
    /**
     * 用户名不存在
     */
    USERNAME_NOT_FOUND("username_not_found", "用户名不存在"),
    
    /**
     * 请求方式不支持
     */
    REQUEST_METHOD_NOT_SUPPORTED("request_method_not_supported", "请求方式不支持"),
    
    /**
     * Json参数格式错误
     */
    JSON_FORMAT("json_format", "Json参数格式错误"),
    
    /**
     * 违反了数据库的唯一约束条件
     */
    DUPLICATE_KEY("duplicate_key", "违反了数据库的唯一约束条件"),

    /**
     * 用户名或密码错误
     */
    USERNAME_PASSWORD_ERROR("username_password_error", "用户名或密码错误"),

    /**
     * 系统异常
     */
    SYSTEM("SYSTEM", "系统异常");
    
    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;

}
