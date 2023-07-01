package cn.kun.auth.system.job.service;

import cn.kun.auth.system.job.entity.dto.JobMenuDTO;
import cn.kun.auth.system.job.entity.po.SysJobMenuInner;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 内部职位菜单表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysJobMenuInnerService extends IService<SysJobMenuInner> {
    /**
     * 保存
     * @param dto 职位菜单内部关联-传入值
     */
    void save(JobMenuDTO dto);
}
