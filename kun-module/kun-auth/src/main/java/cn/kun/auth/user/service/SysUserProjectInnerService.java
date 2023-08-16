package cn.kun.auth.user.service;

import cn.kun.auth.user.entity.dto.UserProjectDTO;
import cn.kun.auth.user.entity.po.SysUserProjectInner;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 内部用户项目表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysUserProjectInnerService extends IService<SysUserProjectInner> {
    /**
     * 保存数据
     * @param dto
     * @return
     */
    void save(UserProjectDTO dto);

    /**
     * 获取项目id
     *
     * @param dto
     * @return
     */
    String getProjectNosByUserId(UserProjectDTO dto);


}
