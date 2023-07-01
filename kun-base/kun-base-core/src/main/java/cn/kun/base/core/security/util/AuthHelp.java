package cn.kun.base.core.security.util;

import cn.hutool.core.util.ObjUtil;
import cn.kun.base.core.security.entity.LoginUser;
import cn.kun.base.core.security.entity.UserInfo;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

/**
 * 认证授权工具类
 *
 * @author 廖航
 */
public class AuthHelp {

    /**
     * 获取登录用户
     * @return 登录用户
     */
    public static LoginUser getLoginUser() {
        /* 安全信息上下文 */
        SecurityContext context = SecurityContextHolder.getContext();
        if (ObjUtil.isNull(context)) {
            return null;
        }

        /* 认证信息 */
        Authentication authentication = context.getAuthentication();
        if (ObjUtil.isNull(authentication)) {
            return null;
        }

        // 转换为登录用户
        return cast(authentication.getPrincipal());
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    public static UserInfo getUserInfo() {
        LoginUser loginUser = getLoginUser();
        if (ObjUtil.isNull(loginUser)) {
            return null;
        }
        return loginUser.getUserInfo();
    }

    /**
     * 获取权限标识列表
     * @return 权限标识列表
     */
    public static List<String> getPermissions() {
        LoginUser loginUser = getLoginUser();
        if (ObjUtil.isNull(loginUser)) {
            return null;
        }
        return loginUser.getPermissions();
    }

    /**
     * 获取用户ID
     * @return 用户ID
     */
    public static String getUserId() {
        UserInfo userInfo = getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            return null;
        }
        return userInfo.getId();
    }

    /**
     * 获取登录名
     * @return 登录名
     */
    public static String getLoginName() {
        UserInfo userInfo = getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            return null;
        }
        return userInfo.getLoginName();
    }

    /**
     * 获取密码
     * @return 密码
     */
    public static String getPassword() {
        UserInfo userInfo = getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            return null;
        }
        return userInfo.getPassword();
    }

    /**
     * 获取名称
     * @return 名称
     */
    public static String getName() {
        UserInfo userInfo = getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            return null;
        }
        return userInfo.getName();
    }

    /**
     * 获取头像
     * @return 头像文件路径
     */
    public static String getAvatar() {
        UserInfo userInfo = getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            return null;
        }
        return userInfo.getAvatar();
    }

    /**
     * 获取状态
     * @return 状态
     */
    public static String getStatus() {
        UserInfo userInfo = getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            return null;
        }
        return userInfo.getStatus();
    }
    
    /**
     * 获取所属公司ID
     * @return 所属公司ID
     */
    public static String getCompanyId() {
        UserInfo userInfo = getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            return null;
        }
        return userInfo.getCompanyId();
    }

    /**
     * 获取内外部标识
     * @return 内外部标识
     */
    public static boolean getCharacteristic() {
        UserInfo userInfo = getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "用户信息为空，无法查询内外部标识");
        }
        return userInfo.getCharacteristic();
    }

    /**
     * 获取项目编码列表
     * @return 项目编码列表
     */
    public static List<String> getProjectNoList() {
        UserInfo userInfo = getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            return null;
        }
        return userInfo.getProjectNoList();
    }

    /**
     * 认证对象转化为登录用户对象
     * @param principal 认证对象
     * @return 登录用户对象
     */
    public static LoginUser cast(Object principal) {
        if (principal instanceof LoginUser loginUser) {
            return loginUser;
        } else {
            throw new ClassCastException("认证对象不能转化为登录用户对象");
        }
    }
    
}