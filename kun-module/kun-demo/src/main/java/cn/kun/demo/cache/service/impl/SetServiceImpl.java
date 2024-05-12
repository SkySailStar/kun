package cn.kun.demo.cache.service.impl;

import cn.kun.demo.cache.service.SetService;
import cn.kun.demo.cache.entity.dto.SetDelDTO;
import cn.kun.demo.cache.entity.dto.SetHashDTO;
import cn.kun.demo.cache.entity.dto.SetSetDTO;
import cn.kun.demo.cache.entity.dto.SetSetTimeDTO;
import org.springframework.stereotype.Service;
import java.util.Set;

/**
 * 集合-服务层实现类
 *
 * @author SkySailStar
 * @date 2023-01-13 15:19
 */
@Service
public class SetServiceImpl implements SetService {

    @Override
    public Set<Object> get(String key) {
        return RedisHelp.getSet(key);
    }

    @Override
    public Object randomSet(String key) {
        return RedisHelp.randomSet(key);
    }

    @Override
    public long getSize(String key) {
        return RedisHelp.getSetSize(key);
    }

    @Override
    public void set(SetSetDTO dto) {
        RedisHelp.setSet(dto.getKey(), dto.getValue());
    }

    @Override
    public void set(SetSetTimeDTO dto) {
        RedisHelp.setSetTime(dto.getKey(), dto.getTime(), dto.getValue());
    }

    @Override
    public boolean has(SetHashDTO dto) {
        return RedisHelp.hasSet(dto.getKey(), dto.getValue());
    }

    @Override
    public void del(SetDelDTO dto) {
        RedisHelp.delSet(dto.getKey(), dto.getValue());
    }
}
