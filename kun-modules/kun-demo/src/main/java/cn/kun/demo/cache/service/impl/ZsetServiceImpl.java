package cn.kun.demo.cache.service.impl;

import cn.kun.demo.cache.service.ZsetService;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.demo.cache.entity.dto.ZsetDelDTO;
import cn.kun.demo.cache.entity.dto.ZsetGetDTO;
import cn.kun.demo.cache.entity.dto.ZsetIncDTO;
import cn.kun.demo.cache.entity.dto.ZsetRankDTO;
import cn.kun.demo.cache.entity.dto.ZsetSetDTO;
import cn.kun.demo.cache.entity.dto.ZsetSetSetDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 有序列表-服务层实现类
 *
 * @author 廖航
 * @date 2023-03-10 18:18
 */
@Service
public class ZsetServiceImpl implements ZsetService {
    
    @Override
    public void set(ZsetSetDTO dto) {
        RedisHelp.setZset(dto.getKey(), dto.getValue(), dto.getScore());
    }
    
    @Override
    public void setSet(ZsetSetSetDTO dto) {
        RedisHelp.setZset(dto.getKey(), dto.getValues());
    }

    @Override
    public Set<Object> get(ZsetGetDTO dto) {
        return RedisHelp.getZset(dto.getKey(), dto.getStart(), dto.getEnd());
    }

    @Override
    public void del(ZsetDelDTO dto) {
        RedisHelp.delZset(dto.getKey(), dto.getValues());
    }

    @Override
    public double inc(ZsetIncDTO dto) {
        return RedisHelp.incScoreZset(dto.getKey(), dto.getValue(), dto.getNum());
    }

    @Override
    public Long rank(ZsetRankDTO dto) {
        return RedisHelp.rankZset(dto.getKey(), dto.getValue());
    }

    @Override
    public Long size(String key) {
        return RedisHelp.getZsetSize(key);
    }
}
