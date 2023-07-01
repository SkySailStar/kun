package cn.kun.demo.crud.service;

import cn.kun.demo.crud.entity.dto.JobMenuInnerSaveDTO;
import cn.kun.demo.crud.entity.po.SysJobMenuInner;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 内部职位菜单表 服务类
 * </p>
 *
 * @author 廖航
 * @since 2023-02-27 17:54
 */
public interface SysJobMenuInnerService extends IService<SysJobMenuInner> {

    /**
     * 保存
     * @param dto 关联-传入值
     * @return 结果
     */
    boolean save(JobMenuInnerSaveDTO dto);
    
}
