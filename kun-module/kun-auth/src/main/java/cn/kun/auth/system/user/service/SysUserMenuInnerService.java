package cn.kun.auth.system.user.service;

import cn.kun.auth.system.user.entity.dto.UserMenuDTO;
import cn.kun.auth.system.user.entity.po.SysUserMenuInner;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 内部用户菜单表 服务类
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:22
 */
public interface SysUserMenuInnerService extends IService<SysUserMenuInner> {
    /**
     * 保存数据
     * @param dto
     * @return
     */
    void save(UserMenuDTO dto);

}
