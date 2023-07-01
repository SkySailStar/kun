package cn.kun.auth.system.role.service;

import cn.kun.auth.system.role.entity.dto.RoleProjectDTO;
import cn.kun.auth.system.role.entity.po.SysRoleProjectOuter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 外部角色项目表 服务类
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:23
 */
public interface SysRoleProjectOuterService extends IService<SysRoleProjectOuter> {
    /**
     * 保存数据
     * @param dto
     * @return
     */
    void save(RoleProjectDTO dto);
}
