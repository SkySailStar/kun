package cn.kun.auth.security.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.kun.auth.security.entity.po.SysUser;
import cn.kun.auth.security.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.security.entity.LoginUser;
import cn.kun.base.core.security.entity.UserInfo;
import cn.kun.base.core.global.exception.BusinessException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户详情服务
 *
 * @author 天航星
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Resource
    private SysUserService sysUserService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 查询用户
        QueryWrapper<SysUser> innerWrapper = new QueryWrapper<>();
        innerWrapper.lambda().eq(SysUser::getUserName, username);
        List<SysUser> sysUserList = sysUserService.list(innerWrapper);
        if (sysUserList.size() > 1) {
            log.warn("存在重复的内部用户名");
            throw new BusinessException(ErrorCodeConstants.REPEAT, "存在重复的内部用户名");
        }
        SysUser sysUser = sysUserList.get(0);
        if (!sysUser.getStatus()) {
            log.warn("用户已停用");
            throw new BusinessException(ErrorCodeConstants.DISABLE, "用户已停用");
        }
        // 验证登录名大小写是否一致
        if (!ObjUtil.equals(username, sysUser.getUserName())) {
            log.warn("用户名或密码错误");
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        /* 封装成登录用户信息返回 */
        UserInfo userInfo = new UserInfo();
        // id
        userInfo.setId(sysUser.getId());
        // 账号
        userInfo.setUserName(sysUser.getUserName());
        // 密码
        userInfo.setPassword(sysUser.getPassword());
        userInfo.setNickName(sysUser.getNickName());
        userInfo.setStatus(sysUser.getStatus());
        // TODO 增加权限列表
        List<String> permissionList = new ArrayList<>();
        return new LoginUser(userInfo, permissionList);
    }

}