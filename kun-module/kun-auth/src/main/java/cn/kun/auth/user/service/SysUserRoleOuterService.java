package cn.kun.auth.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sevnce.auth.user.entity.dto.RoleUserDTO;
import com.sevnce.auth.user.entity.dto.UserRoleDTO;
import com.sevnce.auth.user.entity.po.SysUserRoleOuter;

/**
 * <p>
 * 外部用户角色表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysUserRoleOuterService extends IService<SysUserRoleOuter> {
    /**
     * 保存数据
     * @param dto
     * @return
     */
    void save(UserRoleDTO dto);

    /**
     * 保存数据
     * @param dto
     * @return
     */
    void save(RoleUserDTO dto);

}
