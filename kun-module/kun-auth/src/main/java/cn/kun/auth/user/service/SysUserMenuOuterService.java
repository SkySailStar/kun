package cn.kun.auth.user.service;

import cn.kun.auth.user.entity.dto.UserMenuDTO;
import cn.kun.auth.user.entity.po.SysUserMenuOuter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 外部用户菜单表 服务类
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:21
 */
public interface SysUserMenuOuterService extends IService<SysUserMenuOuter> {
    /**
     * 保存数据
     * @param dto
     * @return
     */
    void save(UserMenuDTO dto);
}
