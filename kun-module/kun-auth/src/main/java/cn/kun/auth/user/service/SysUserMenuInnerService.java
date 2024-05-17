package cn.kun.auth.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sevnce.auth.user.entity.dto.UserMenuDTO;
import com.sevnce.auth.user.entity.po.SysUserMenuInner;

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
