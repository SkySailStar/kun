package cn.kun.base.core.security.custom;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.base.core.security.util.AuthHelp;
import cn.kun.base.core.global.constant.AuthConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限校验
 *
 * @author 廖航
 */
@Component("custom")
public class CustomAuth {

    /**
     * 判断是否具有权限
     * @param authority 权限标识
     * @return 结果
     */
    public boolean hasAuthority(String authority) {
        
        // 获取登录名
        String loginName = AuthHelp.getLoginName();
        // 超级管理员账号具有最高权限，能够访问所有接口
        if (StrUtil.equals(loginName, AuthConstants.SYS_ADMIN)) {
            return true;
        }
        // 获取权限列表
        List<String> permissions = AuthHelp.getPermissions();
        if (CollUtil.isEmpty(permissions)) {
            throw new BusinessException(ErrorCodeConstants.NO_AUTH, "权限不足，请联系管理员授权");
        }
        // 判断是否具有权限
        if (permissions.contains(authority)) {
            return true;
        } else {
            throw new BusinessException(ErrorCodeConstants.NO_AUTH, "权限不足，请联系管理员授权");
        }
    }

}