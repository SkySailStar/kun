package cn.kun.base.core.security.util;

import cn.hutool.core.util.ObjUtil;
import cn.kun.base.core.security.entity.LoginUser;
import cn.kun.base.core.security.entity.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

/**
 * 认证授权工具类
 *
 * @author 天航星
 */
public class AuthUtils {

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
        return userInfo.getUserName();
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
     * 获取姓名
     * @return 姓名
     */
    public static String getName() {
        UserInfo userInfo = getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            return null;
        }
        return userInfo.getNickName();
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
    public static Boolean getStatus() {
        UserInfo userInfo = getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            return null;
        }
        return userInfo.getStatus();
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