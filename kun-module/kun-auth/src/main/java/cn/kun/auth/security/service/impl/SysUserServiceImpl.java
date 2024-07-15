package cn.kun.auth.security.service.impl;

import cn.kun.auth.security.entity.po.SysUser;
import cn.kun.auth.security.mapper.SysUserMapper;
import cn.kun.auth.security.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户-服务层实现类
 *
 * @author 夏扬威
 * @date 2024-04-24 10:08
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    
}
