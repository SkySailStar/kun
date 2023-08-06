package cn.kun.base.core.data.service.impl;

import cn.kun.base.core.data.mapper.CustomBaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import cn.kun.base.core.data.service.CustomBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 自定义公用服务层实现类
 *
 * @author SkySailStar
 * @date 2023-02-28 13:52
 */
@Service
public class CustomBaseServiceImpl<M extends CustomBaseMapper<T>, T> implements CustomBaseService<T> {

    @Autowired
    protected M customBaseMapper;
    
    @Override
    public int updateBatchById(Collection<T> entityList, Wrapper<T> updateWrapper) {
        return customBaseMapper.updateBatchById(entityList, updateWrapper);
    }
}
