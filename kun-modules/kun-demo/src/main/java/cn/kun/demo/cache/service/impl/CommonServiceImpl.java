package cn.kun.demo.cache.service.impl;

import cn.kun.demo.cache.service.CommonService;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.demo.cache.entity.dto.ExpireDTO;
import org.springframework.stereotype.Service;

/**
 * 通用-服务层实现类
 *
 * @author 廖航
 * @date 2023-01-13 14:34
 */
@Service
public class CommonServiceImpl implements CommonService {
    
    @Override
    public void setDatabase(int dbIndex) {
        RedisHelp.select(dbIndex);
    }

    @Override
    public boolean expire(ExpireDTO dto) {
        return RedisHelp.expire(dto.getKey(), dto.getTime());
    }

    @Override
    public long getExpire(String key) {
        return RedisHelp.getExpire(key);
    }

    @Override
    public boolean has(String key) {
        return RedisHelp.has(key);
    }

    @Override
    public void del(String key) {
        RedisHelp.del(key);
    }
}
