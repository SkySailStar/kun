package cn.kun.auth.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.auth.security.entity.po.SysMenu;
import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysMenuService extends IService<SysMenu> {
    
    /**
     * 根据用户ID查询权限列表
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectPermissionList(String userId);
}
