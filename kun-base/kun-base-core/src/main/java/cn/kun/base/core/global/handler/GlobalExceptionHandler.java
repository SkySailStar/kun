package cn.kun.base.core.global.handler;

import cn.hutool.core.util.ReUtil;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.RegularExpressionConstants;
import cn.kun.base.core.global.constant.dict.data.ErrorTypeConstants;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.enums.ErrorInfoEnum;
import cn.kun.base.core.mq.constant.QueueConstants;
import cn.kun.base.core.mq.entity.dto.ErrorInfoMqDTO;
import cn.kun.base.core.mq.service.RabbitService;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import cn.kun.base.core.global.constant.HttpStatusConstants;
import cn.kun.base.core.global.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 全局异常处理器
 *
 * @author 天航星
 * @date 2023-01-30 11:17
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    private RabbitService rabbitService;
    
    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResult<String> handleServiceException(BusinessException e, HttpServletRequest request) {
        
        // 添加到错误信息表
        saveErrorInfo(e.getCode(), e.getMessage(), ErrorTypeConstants.BUSINESS);
        log.error("请求地址：{}, 业务异常：{}", request.getRequestURI(), e.getMessage());
        return BaseResult.fail(e.getCode(), e.getMessage());
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public BaseResult<String> handleNullPointerException(NullPointerException e, HttpServletRequest request) {

        String message = ErrorInfoEnum.NULL_POINTER.getDesc();
        // 添加到错误信息表
        saveErrorInfo(ErrorInfoEnum.NULL_POINTER.getCode(), message, ErrorTypeConstants.SYSTEM);
        log.error("请求地址：{}, {}", request.getRequestURI(), message, e);
        return BaseResult.fail(message);
    }
    
    /**
     * 用户名不存在异常
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public BaseResult<String> handleUsernameNotFoundException(AuthenticationException e, HttpServletRequest request) {

        String message = ErrorInfoEnum.USERNAME_NOT_FOUND.getDesc();
        // 添加到错误信息表
        saveErrorInfo(ErrorInfoEnum.USERNAME_NOT_FOUND.getCode(), message, ErrorTypeConstants.SYSTEM);
        log.error("请求地址：{}, {}", request.getRequestURI(), message, e);
        return BaseResult.fail(HttpStatusConstants.FORBIDDEN, message);
    }

    /**
     * 请求方式不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResult<String> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {

        String message = ErrorInfoEnum.REQUEST_METHOD_NOT_SUPPORTED.getDesc();
        // 添加到错误信息表
        saveErrorInfo(ErrorInfoEnum.REQUEST_METHOD_NOT_SUPPORTED.getCode(), message, ErrorTypeConstants.SYSTEM);
        log.error("请求地址：{}，{}：{}", request.getRequestURI(), message, e.getMethod());
        return BaseResult.fail(HttpStatusConstants.BAD_METHOD, message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        // 添加到错误信息表
        saveErrorInfo(ErrorCodeConstants.VALID_FAIL, message, ErrorTypeConstants.SYSTEM);
        log.error(message, e);
        return BaseResult.fail(message);
    }

    /**
     * Json参数格式错误
     */
    @ExceptionHandler(MismatchedInputException.class)
    public BaseResult<String> handleMismatchedInputException(MismatchedInputException e, HttpServletRequest request) {

        String message = ErrorInfoEnum.JSON_FORMAT.getDesc();
        // 添加到错误信息表
        saveErrorInfo(ErrorInfoEnum.JSON_FORMAT.getCode(), message, ErrorTypeConstants.SYSTEM);
        log.error("请求地址：{}，{}", request.getRequestURI(), message, e);
        return BaseResult.fail(message);
    }

    /**
     * 违反了数据库的唯一约束条件
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public BaseResult<String> handleDuplicateKeyException(DuplicateKeyException e, HttpServletRequest request) {

        String message = ErrorInfoEnum.DUPLICATE_KEY.getDesc();
        // 添加到错误信息表
        saveErrorInfo(ErrorInfoEnum.DUPLICATE_KEY.getCode(), message, ErrorTypeConstants.SYSTEM);
        log.error("请求地址：{}，{}", request.getRequestURI(), message, e);
        return BaseResult.fail(message);
    }
    
    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public BaseResult<String> handleException(Exception e, HttpServletRequest request) {

        String message = e.getMessage();
        if (!ReUtil.isMatch(RegularExpressionConstants.CHINESE, message)) {
            message = ErrorInfoEnum.SYSTEM.getDesc();
        }
        // 添加到错误信息表
        saveErrorInfo(ErrorInfoEnum.SYSTEM.getCode(), message, ErrorTypeConstants.SYSTEM);
        log.error("请求地址：{}，{}：", request.getRequestURI(), ErrorInfoEnum.SYSTEM.getDesc(), e);
        return BaseResult.fail(message);
    }

    /**
     * 保存错误信息
     * @param code 编码
     * @param message 消息
     * @param type 类型
     */
    private void saveErrorInfo(String code, String message, String type) {
        
        // 添加到错误信息表
        try {
            rabbitService.send(QueueConstants.DIRECT_SYSTEM_ERROR_INFO, ErrorInfoMqDTO.of(code, message, type));                    
        } catch (Exception e) {
            log.error("系统服务-错误信息-添加：调用失败", e);
        }
    }

}
