package cn.kun.auth.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sevnce.auth.user.entity.dto.UserProjectDTO;
import com.sevnce.auth.user.entity.po.SysUserProjectOuter;

/**
 * <p>
 * 外部用户项目表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysUserProjectOuterService extends IService<SysUserProjectOuter> {
    /**
     * 保存数据
     * @param dto
     */
    void save(UserProjectDTO dto);


}
