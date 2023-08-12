package cn.kun.auth.system.job.service;

import cn.kun.auth.system.job.entity.dto.JobMenuDTO;
import cn.kun.auth.system.job.entity.po.SysJobMenuOuter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 外部职位菜单表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysJobMenuOuterService extends IService<SysJobMenuOuter> {
    /**
     * 保存数据
     * @param dto
     * @return
     */
    void save(JobMenuDTO dto);

}
