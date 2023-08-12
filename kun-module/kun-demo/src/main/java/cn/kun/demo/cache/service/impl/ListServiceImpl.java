package cn.kun.demo.cache.service.impl;

import cn.kun.demo.cache.entity.dto.ListDelDTO;
import cn.kun.demo.cache.entity.dto.ListSetIndexDTO;
import cn.kun.demo.cache.entity.dto.ListSetListTimeDTO;
import cn.kun.demo.cache.service.ListService;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.demo.cache.entity.dto.ListGetByIndexDTO;
import cn.kun.demo.cache.entity.dto.ListGetDTO;
import cn.kun.demo.cache.entity.dto.ListSetDTO;
import cn.kun.demo.cache.entity.dto.ListSetListDTO;
import cn.kun.demo.cache.entity.dto.ListSetTimeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 列表-服务层实现类
 *
 * @author SkySailStar
 * @date 2023-01-13 15:06
 */
@Service
public class ListServiceImpl implements ListService {

    @Override
    public List<Object> get(String key) {
        return RedisHelp.getList(key);
    }

    @Override
    public List<Object> get(ListGetDTO dto) {
        return RedisHelp.getList(dto.getKey(), dto.getStart(), dto.getEnd());
    }

    @Override
    public long getSize(String key) {
        return RedisHelp.getListSize(key);
    }

    @Override
    public Object getByIndex(ListGetByIndexDTO dto) {
        return RedisHelp.getList(dto.getKey(), dto.getIndex());
    }

    @Override
    public void set(ListSetDTO dto) {
        RedisHelp.setList(dto.getKey(), dto.getValue());
    }

    @Override
    public void set(ListSetTimeDTO dto) {
        RedisHelp.setList(dto.getKey(), dto.getValue(), dto.getTime());
    }

    @Override
    public void set(ListSetListDTO dto) {
        RedisHelp.setList(dto.getKey(), dto.getValue());
    }

    @Override
    public void set(ListSetListTimeDTO dto) {
        RedisHelp.setList(dto.getKey(), dto.getValue(), dto.getTime());
    }

    @Override
    public void set(ListSetIndexDTO dto) {
        RedisHelp.setList(dto.getKey(), dto.getIndex(), dto.getValue());
    }

    @Override
    public void del(ListDelDTO dto) {
        RedisHelp.delList(dto.getKey(), dto.getCount(), dto.getValue());
    }

    @Override
    public void pop(String key) {
        RedisHelp.popList(key);
    }
}
