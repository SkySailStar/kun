package cn.kun.auth.security.service.impl;

import cn.kun.auth.security.entity.po.SysMenu;
import cn.kun.auth.security.mapper.SysMenuMapper;
import cn.kun.auth.security.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<String> selectPermissionList(String userId) {
        return baseMapper.selectPermissionList(userId);
    }
}
