package cn.kun.auth.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sevnce.auth.user.entity.dto.UserJobDTO;
import com.sevnce.auth.user.entity.po.SysUserJobOuter;

/**
 * <p>
 * 外部用户职位表 服务类
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:21
 */
public interface SysUserJobOuterService extends IService<SysUserJobOuter> {
    /**
     * 保存数据
     * @param dto
     * @return
     */
    void save(UserJobDTO dto);

}
