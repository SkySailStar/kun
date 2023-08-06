package cn.kun.base.core.data.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.Collection;

/**
 * 自定义公用服务层
 *
 * @author SkySailStar
 * @date 2023-02-28 13:51
 */
public interface CustomBaseService<T> {

    /**
     * 通过ID批量更新数据
     *
     * @param entityList 实体列表
     * @param updateWrapper 更新条件
     * @return 影响行数
     */
    int updateBatchById(Collection<T> entityList, Wrapper<T> updateWrapper);
    
}
