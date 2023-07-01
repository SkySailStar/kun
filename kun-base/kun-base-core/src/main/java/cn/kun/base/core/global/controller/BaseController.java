package cn.kun.base.core.global.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.kun.base.core.global.constant.BaseConstants;
import cn.kun.base.core.global.constant.HttpStatusConstants;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;

/**
 * web层通用数据处理
 *
 * @author 廖航
 */
@Slf4j
public class BaseController {

    /**
     * 成功
     */
    public static final String SUCCESS = Convert.toStr(HttpStatusConstants.SUCCESS);

    /**
     * 失败
     */
    public static final String FAIL = Convert.toStr(HttpStatusConstants.ERROR);

    /**
     * 成功消息
     */
    public static final String SUCCESS_MSG = BaseConstants.SUCCESS_MSG;

    /**
     * 失败消息
     */
    public static final String FAIL_MSG = BaseConstants.FAIL_MSG;

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected <T> BaseResult<T> toRows(int rows) {
        return rows > 0 ? success() : fail();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected <T> BaseResult<T> toSuccess(boolean result) {
        return result ? success() : fail();
    }

    protected <T> BaseResult<T> success() {
        return restResult(null, SUCCESS, SUCCESS_MSG);
    }

    protected <T> BaseResult<T> success(T data) {
        return restResult(data, SUCCESS, SUCCESS_MSG);
    }

    protected <T> BaseResult<T> success(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    protected <T> BaseResult<T> fail() {
        return restResult(null, FAIL, FAIL_MSG);
    }

    protected <T> BaseResult<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    protected <T> BaseResult<T> fail(T data) {
        return restResult(data, FAIL, FAIL_MSG);
    }

    protected <T> BaseResult<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    protected <T> BaseResult<T> fail(String code, String msg) {
        return restResult(null, code, msg);
    }

    protected <T> BaseResult<T> restResult(T data, String code, String msg) {
        BaseResult<T> apiResult = new BaseResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    protected <T> Boolean isFail(BaseResult<T> rest) {
        return !isSuccess(rest);
    }

    protected <T> Boolean isSuccess(BaseResult<T> rest) {
        return ObjUtil.equals(SUCCESS, rest.getCode());
    }

}