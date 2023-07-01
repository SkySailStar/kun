package cn.kun.auth.system.role.service;

import cn.kun.auth.system.role.entity.dto.RoleMenuDTO;
import cn.kun.auth.system.role.entity.po.SysRoleMenuOuter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 外部角色菜单表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysRoleMenuOuterService extends IService<SysRoleMenuOuter> {
    /**
     * 保存数据
     * @param dto
     * @return
     */
    void save(RoleMenuDTO dto);
}
