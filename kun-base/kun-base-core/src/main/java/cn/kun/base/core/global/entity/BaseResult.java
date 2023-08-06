package cn.kun.base.core.global.entity;

import cn.hutool.core.util.ObjUtil;
import cn.kun.base.core.global.constant.BaseConstants;
import cn.kun.base.core.global.constant.HttpStatusConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * 公共响应
 *
 * @author SkySailStar
 */
@Schema(description = "返回值")
public class BaseResult<T> implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "消息")
    private String msg;

    @Schema(description = "结果")
    private T data;
    
    /**
     * 成功
     */
    public static final String SUCCESS = HttpStatusConstants.SUCCESS;

    /**
     * 失败
     */
    public static final String FAIL = HttpStatusConstants.ERROR;

    /**
     * 成功消息
     */
    public static final String SUCCESS_MSG = BaseConstants.SUCCESS_MSG;

    /**
     * 失败消息
     */
    public static final String FAIL_MSG = BaseConstants.FAIL_MSG;

    public static <T> BaseResult<T> success() {
        return restResult(null, SUCCESS, SUCCESS_MSG);
    }

    public static <T> BaseResult<T> success(T data) {
        return restResult(data, SUCCESS, SUCCESS_MSG);
    }

    public static <T> BaseResult<T> success(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> BaseResult<T> fail() {
        return restResult(null, FAIL, FAIL_MSG);
    }

    public static <T> BaseResult<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> BaseResult<T> fail(T data) {
        return restResult(data, FAIL, FAIL_MSG);
    }

    public static <T> BaseResult<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> BaseResult<T> fail(String code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> BaseResult<T> restResult(T data, String code, String msg) {
        BaseResult<T> apiResult = new BaseResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Boolean isFail(BaseResult<T> rest) {
        return !isSuccess(rest);
    }

    public static <T> Boolean isSuccess(BaseResult<T> rest) {
        return ObjUtil.equals(SUCCESS, rest.getCode());
    }
}
