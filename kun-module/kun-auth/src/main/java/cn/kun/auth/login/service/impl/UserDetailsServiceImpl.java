package cn.kun.auth.login.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.kun.auth.user.entity.po.SysUserInner;
import cn.kun.auth.user.entity.po.SysUserOuter;
import cn.kun.auth.user.service.SysUserInnerService;
import cn.kun.auth.user.service.SysUserOuterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.kun.auth.menu.service.SysMenuService;
import cn.kun.auth.project.service.SysProjectService;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.util.convert.ConvertHelp;
import cn.kun.base.core.security.entity.LoginUser;
import cn.kun.base.core.security.entity.UserInfo;
import cn.kun.base.core.global.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户详情服务
 *
 * @author SkySailStar
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserInnerService sysUserInnerService;
    
    @Resource
    private SysUserOuterService sysUserOuterService;
    
    @Resource
    private SysMenuService sysMenuService;
    
    @Resource
    private SysProjectService sysProjectService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 先查询内部用户
        QueryWrapper<SysUserInner> innerWrapper = new QueryWrapper<>();
        innerWrapper.lambda().eq(SysUserInner::getLoginName, username);
        List<SysUserInner> sysUserInnerList = sysUserInnerService.list(innerWrapper);
        if (sysUserInnerList.size() > 1) {
            log.warn("存在重复的内部用户名");
            throw new BusinessException(ErrorCodeConstants.REPEAT, "存在重复的内部用户名");
        }
        UserInfo userInfo = new UserInfo();
        // 用户ID
        Long userId;
        // 登录名
        String loginName;
        // 密码
        String password;
        // 用户名
        String name;
        // 头像
        String avatar;
        // 状态
        String status;
        // 公司ID
        Long companyId;
        // 项目编码列表
        List<String> projectNoList;
        // 内外部标识
        boolean characteristic;
        // 权限列表
        List<String> permissions;
        // 如果查询不到再查询外部用户
        if (sysUserInnerList.isEmpty()) {
            QueryWrapper<SysUserOuter> outerWrapper = new QueryWrapper<>();
            outerWrapper.lambda().eq(SysUserOuter::getLoginName, username);
            List<SysUserOuter> sysUserOuterList = sysUserOuterService.list(outerWrapper);
            if (sysUserOuterList.size() > 1) {
                log.warn("存在重复的外部用户名");
                throw new BusinessException(ErrorCodeConstants.REPEAT, "存在重复的外部用户名");
            }
            if (sysUserOuterList.isEmpty()) {
                log.warn("用户名或密码错误");
                throw new UsernameNotFoundException("用户名或密码错误");
            }
            SysUserOuter sysUserOuter = sysUserOuterList.get(0);
            userId = sysUserOuter.getId();
            loginName = sysUserOuter.getLoginName();
            password = sysUserOuter.getPassword();
            name = sysUserOuter.getName();
            avatar = sysUserOuter.getPath();
            status = sysUserOuter.getStatus();
            companyId = sysUserOuter.getCompanyOuterId();
            characteristic = false;
            projectNoList = sysProjectService.selectProjectNo(characteristic, userId, loginName);
            permissions = sysMenuService.selectPermsOuter(sysUserOuter.getId());
        } else {
            SysUserInner sysUserInner = sysUserInnerList.get(0);
            userId = sysUserInner.getId();
            loginName = sysUserInner.getLoginName();
            password = sysUserInner.getPassword();
            name = sysUserInner.getName();
            avatar = sysUserInner.getPath();
            status = sysUserInner.getStatus();
            companyId = sysUserInner.getCompanyInnerId();
            characteristic = true;
            projectNoList = sysProjectService.selectProjectNo(characteristic, userId, loginName);
            permissions = sysMenuService.selectPermsInner(sysUserInner.getId());
        }
        // 验证登录名大小写是否一致
        if (!StrUtil.equals(loginName, username)) {
            log.warn("用户名或密码错误");
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 封装成登录用户信息返回
        userInfo.setId(ConvertHelp.toStr(userId));
        userInfo.setLoginName(loginName);
        userInfo.setPassword(password);
        userInfo.setName(name);
        userInfo.setAvatar(avatar);
        userInfo.setStatus(status);
        userInfo.setCompanyId(ConvertHelp.toStr(companyId));
        userInfo.setProjectNoList(projectNoList);
        userInfo.setCharacteristic(characteristic);
        return new LoginUser(userInfo, permissions);
    }

}