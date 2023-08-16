package cn.kun.auth.role.service;

import cn.kun.auth.role.entity.dto.RoleMenuDTO;
import cn.kun.auth.role.entity.po.SysRoleMenuInner;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 内部角色菜单表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysRoleMenuInnerService extends IService<SysRoleMenuInner> {
    /**
     * 保存数据
     * @param dto
     * @return
     */
    void save(RoleMenuDTO dto);
}
