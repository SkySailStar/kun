package cn.kun.base.core.data.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * 自定义公用持久层
 * 
 * @author 廖航
 * @date 2023-02-28 11:50
 */
public interface CustomBaseMapper<T> extends BaseMapper<T> {
    
    /**
     * 通过ID批量更新数据
     *
     * @param entityList 实体列表
     * @param updateWrapper 更新条件
     * @return 影响行数
     */
    int updateBatchById(@Param("list") Collection<T> entityList, @Param("ew") Wrapper<T> updateWrapper);
}
