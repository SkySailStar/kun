package cn.kun.auth.system.user.service;

import cn.kun.auth.system.user.entity.dto.UserJobDTO;
import cn.kun.auth.system.user.entity.po.SysUserJobOuter;
import com.baomidou.mybatisplus.extension.service.IService;

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
