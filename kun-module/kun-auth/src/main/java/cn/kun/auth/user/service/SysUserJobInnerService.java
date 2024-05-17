package cn.kun.auth.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sevnce.auth.user.entity.dto.UserJobDTO;
import com.sevnce.auth.user.entity.po.SysUserJobInner;

/**
 * <p>
 * 内部用户职位表 服务类
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:08
 */
public interface SysUserJobInnerService extends IService<SysUserJobInner> {
    /**
     * 保存数据
     * @param dto
     * @return
     */
    void save(UserJobDTO dto);
}
