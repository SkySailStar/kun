package cn.kun.auth.security.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.kun.base.api.service.auth.LoginService;
import cn.kun.base.core.cache.constant.AuthCacheConstants;
import cn.kun.base.core.cache.util.RedisUtils;
import cn.kun.base.core.global.util.convert.ConvertUtils;
import cn.kun.base.core.security.constant.LoginConstants;
import cn.kun.base.core.security.entity.LoginUser;
import cn.kun.base.core.security.entity.UserInfo;
import cn.kun.base.core.security.entity.dto.CheckTokenDTO;
import cn.kun.base.core.security.entity.dto.LoginDTO;
import cn.kun.base.core.security.entity.vo.LoginVO;
import cn.kun.base.core.security.util.AuthUtils;
import cn.kun.base.core.security.util.JwtUtils;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 认证-服务层实现类
 *
 * @author 天航星
 */
@DubboService
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationConfiguration authenticationConfiguration;
    
    @Resource
    private UserDetailsService userDetailsService;
    
    @Override
    public LoginVO login(LoginDTO dto) throws Exception {

        // 封装传入的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getLoginName(), dto.getPassword());
        /*
        执行后会调用 UserDetailsService#loadUserByUsername 从数据库查询用户信息
        如果查询出来的用户名和密码与传入的不一致，则会报错
         */
        Authentication authenticate = authenticationConfiguration.getAuthenticationManager().authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            log.warn("用户名或密码错误");
            throw new BusinessException(ErrorCodeConstants.USERNAME_PASSWORD_ERROR, "用户名或密码错误");
        }
        // 当前登录用户信息
        LoginUser loginUser = AuthUtils.cast(authenticate.getPrincipal());
        if (ObjUtil.isNull(loginUser)) {
            log.warn("未获取到登录用户");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "未获取到登录用户");
        }
        // 用户信息
        UserInfo userInfo = loginUser.getUserInfo();
        if (ObjUtil.isNull(userInfo)) {
            log.warn("未获取到用户信息");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "未获取到用户信息");
        }
        // 用户ID
        String userId = userInfo.getId();
        // 如果用户被限制登录
        if (RedisUtils.has(AuthCacheConstants.LOGIN_LIMIT + userId)) {
            long expire = RedisUtils.getExpire(AuthCacheConstants.LOGIN_LIMIT + userId);
            log.warn("密码错误次数过多，已被限制登录，请" + expire + "秒后重试");
            throw new BusinessException(ErrorCodeConstants.LOGIN_LIMIT, "密码错误次数过多，已被限制登录，请" + expire + "秒后重试");
        }
        // 有效时间（单位：分钟）
        Long validTime = dto.getValidTime();
        // 如果未传入有效时间，则设置为默认值（3天）
        if (ObjUtil.isNull(validTime) || ObjUtil.equals(validTime, 0L)) {
            validTime = LoginConstants.JWT_TTL;
        }
        // 生成token
        String token = JwtUtils.create(ConvertUtils.toStr(userId), validTime * 60 * 1000L);
        // 登录用户信息存入Redis
        RedisUtils.setHash(AuthCacheConstants.LOGIN_INFO_HASH, Convert.toStr(userId), loginUser);
        // 返回值
        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setValidTime(validTime);
        return vo;
    }

    @Override
    public void logout() {
        
        // 获取用户ID
        String userId = AuthUtils.getUserId();
        // 清除缓存的登录信息
        RedisUtils.delHash(AuthCacheConstants.LOGIN_INFO_HASH, Convert.toStr(userId));
    }

    @Override
    public boolean checkToken(CheckTokenDTO dto) {
        // 解析token
        Claims claims;
        try {
            claims = JwtUtils.parse(dto.getToken());
        } catch (Exception e) {
            return false;
        }
        if (ObjUtil.isNull(claims)) {
            return false;
        }
        // 获取过期时间
        Date expiration = claims.getExpiration();
        if (ObjUtil.isNull(expiration)) {
            return false;
        }
        // 跟当前时间比较，大于当前时间则为过期
        return DateUtil.compare(expiration, new Date()) > 0;
    }

    @Override
    public List<String> permissions() {
        return AuthUtils.getPermissions();
    }

    @Override
    public LoginUser getRealUserInfo() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(AuthUtils.getLoginName());
        if (userDetails instanceof LoginUser loginUser) {
            return loginUser;
        }
        return null;
    }

}