package cn.kun.demo.cache.service.impl;

import cn.kun.demo.cache.service.HashService;
import cn.kun.demo.cache.entity.dto.HashDelDTO;
import cn.kun.demo.cache.entity.dto.HashGetByKeyValueDTO;
import cn.kun.demo.cache.entity.dto.HashHasDTO;
import cn.kun.demo.cache.entity.dto.HashIncDecDTO;
import cn.kun.demo.cache.entity.dto.HashSetDTO;
import cn.kun.demo.cache.entity.dto.HashSetTimeDTO;
import cn.kun.demo.cache.entity.dto.HashSetValueDTO;
import cn.kun.demo.cache.entity.dto.HashSetValueTimeDTO;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.demo.cache.entity.dto.HashSetMapDTO;
import cn.kun.demo.cache.entity.dto.HashSetMapTimeDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 哈希-服务层实现类
 *
 * @author SkySailStar
 * @date 2023-01-13 15:29
 */
@Service
public class HashServiceImpl implements HashService {

    @Override
    public Map<Object, Object> get(String key) {
        return RedisHelp.getHash(key);
    }

    @Override
    public Object getByKeyValue(HashGetByKeyValueDTO dto) {
        return RedisHelp.getHash(dto.getKey(), dto.getItem());
    }

    @Override
    public void set(HashSetDTO dto) {
        RedisHelp.setHash(dto.getKey(), dto.getItem(), dto.getValue());
    }

    @Override
    public void set(HashSetTimeDTO dto) {
        RedisHelp.setHash(dto.getKey(), dto.getItem(), dto.getValue(), dto.getTime());
    }

    @Override
    public void set(HashSetMapDTO dto) {
        RedisHelp.setHash(dto.getKey(), dto.getValue());
    }

    @Override
    public void set(HashSetMapTimeDTO dto) {
        RedisHelp.setHash(dto.getKey(), dto.getValue(), dto.getTime());
    }

    @Override
    public void set(HashSetValueDTO dto) {
        RedisHelp.setHash(dto.getKey(), dto.getItem(), dto.getValue());
    }

    @Override
    public void set(HashSetValueTimeDTO dto) {
        RedisHelp.setHash(dto.getKey(), dto.getItem(), dto.getValue(), dto.getTime());
    }

    @Override
    public boolean has(HashHasDTO dto) {
        return RedisHelp.hasHash(dto.getKey(), dto.getItem());
    }

    @Override
    public void del(HashDelDTO dto) {
        RedisHelp.delHash(dto.getKey(), dto.getItem());
    }

    @Override
    public double inc(HashIncDecDTO dto) {
        return RedisHelp.incHash(dto.getKey(), dto.getItem(), dto.getNum());
    }

    @Override
    public double dec(HashIncDecDTO dto) {
        return RedisHelp.decHash(dto.getKey(), dto.getItem(), dto.getNum());
    }
}