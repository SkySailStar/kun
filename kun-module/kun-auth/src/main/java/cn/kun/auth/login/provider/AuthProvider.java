package cn.kun.auth.login.provider;

import cn.hutool.core.convert.Convert;
import cn.kun.base.core.cache.constant.AuthCacheConstants;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.base.core.global.constant.AuthConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.obj.ObjHelp;
import cn.kun.base.core.security.entity.LoginUser;
import cn.kun.base.core.security.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 认证供应端
 * 
 * @author SkySailStar
 */
@Slf4j
@Component
public class AuthProvider implements AuthenticationProvider {
 
    @Resource
    private UserDetailsService userDetailsService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 登录用户名
        String loginName = authentication.getName();
        // 登录密码
        String passWord = "";
        Object credentials = authentication.getCredentials();
        if (credentials == null) {
            throw new BadCredentialsException("凭证为空");
        }
        if (credentials instanceof String credentialsPassWord) {
            passWord = credentialsPassWord;
        }
        // 根据用户名获取用户信息
        UserDetails userDeatils = userDetailsService.loadUserByUsername(loginName);
        // 用户ID
        String userId = "";
        if (userDeatils instanceof LoginUser loginUser) {
            UserInfo userInfo = loginUser.getUserInfo();
            if (ObjHelp.isNotNull(userInfo)) {
                userId = userInfo.getId();
            }
        }
        // 错误消息
        String errorMsg;
        // 登录密码错误缓存
        String loginPasswordError = AuthCacheConstants.LOGIN_PASSWORD_ERROR_HASH;
        // 登录限制缓存
        String loginLimit = AuthCacheConstants.LOGIN_LIMIT;
        // 登录密码错误限制次数
        int loginErrorCount = AuthConstants.LOGIN_PASSWORD_ERROR_COUNT;
        // 登录密码错误限制登录时间（单位：分钟）
        int loginLimitTime = AuthConstants.LOGIN_LIMIT_TIME;
        // 登录密码错误验证时间（单位：分钟）
        int loginPasswordErrorTime = AuthConstants.LOGIN_PASSWORD_ERROR_TIME;
        // 如果用户被限制登录
        if (RedisHelp.has(loginLimit + userId)) {
            long expire = RedisHelp.getExpire(loginLimit + userId);
            if (expire > 60) {
                errorMsg = String.format("密码错误次数过多，已被限制登录，请%d分%d秒后重试", expire / 60, expire % 60);
            } else {
                errorMsg = String.format("密码错误次数过多，已被限制登录，请%d秒后重试", expire);
            }
            throw new BusinessException(ErrorCodeConstants.LOGIN_LIMIT, errorMsg);
        }
        // 密码校验
        if (!new BCryptPasswordEncoder().matches(passWord, userDeatils.getPassword())) {
            // 错误次数
            int errorCount = 1;
            // 如果该用户ID的缓存存在
            if (RedisHelp.hasHash(loginPasswordError, String.valueOf(userId))) {
                errorCount = Convert.toInt(RedisHelp.incHash(loginPasswordError, String.valueOf(userId), 1));
                // 错误次数达到限制
                if (errorCount == loginErrorCount) {
                    // 存入限制登录缓存中
                    RedisHelp.set(loginLimit + userId, loginPasswordErrorTime * 60, loginPasswordErrorTime * 60);
                    // 删除错误次数缓存
                    RedisHelp.delHash(loginPasswordError, String.valueOf(userId));
                    errorMsg = "密码错误次数过多，已被限制登录，请10分钟后重试";
                    throw new BusinessException(ErrorCodeConstants.LOGIN_LIMIT, errorMsg);
                }
            } else {
                // 存入错误次数
                RedisHelp.setHash(loginPasswordError, String.valueOf(userId), errorCount, loginPasswordErrorTime * 60);
            }
            errorMsg = String.format("密码错误！再错误%d次，%d分钟内不能登录", loginErrorCount - errorCount, loginLimitTime);
            throw new BadCredentialsException(errorMsg);
        } else {
            // 如果存在密码错误缓存，则删除
            if (RedisHelp.hasHash(loginPasswordError, Convert.toStr(userId))) {
                RedisHelp.delHash(loginPasswordError, Convert.toStr(userId));
            }
            // 如果存在限制登录缓存，则删除
            if (RedisHelp.has(loginLimit + userId)) {
                RedisHelp.del(loginLimit + userId);
            }
        }
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDeatils, authentication.getCredentials(), userDeatils.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}