package cn.kun.demo.cache.service.impl;

import cn.hutool.core.convert.Convert;
import cn.kun.demo.cache.service.StringService;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.demo.cache.entity.dto.StringIncDecDTO;
import cn.kun.demo.cache.entity.dto.StringSetDTO;
import cn.kun.demo.cache.entity.dto.StringSetIfAbsentDTO;
import cn.kun.demo.cache.entity.dto.StringSetTimeDTO;
import org.springframework.stereotype.Service;

/**
 * 字符串-服务层实现类
 *
 * @author 廖航
 * @date 2023-01-13 14:55
 */
@Service
public class StringServiceImpl implements StringService {

    @Override
    public String get(String key) {
        return Convert.toStr(RedisHelp.get(key));
    }

    @Override
    public void set(StringSetDTO dto) {
        RedisHelp.set(dto.getKey(), dto.getValue());
    }

    @Override
    public void set(StringSetTimeDTO dto) {
        RedisHelp.set(dto.getKey(), dto.getValue(), dto.getTime());
    }

    @Override
    public void setIfAbsent(StringSetIfAbsentDTO dto) {
        RedisHelp.setIfAbsent(dto.getKey(), dto.getValue());
    }
    
    @Override
    public long inc(StringIncDecDTO dto) {
        return RedisHelp.inc(dto.getKey(), dto.getNum());
    }

    @Override
    public long dec(StringIncDecDTO dto) {
        return RedisHelp.dec(dto.getKey(), dto.getNum());
    }
}
