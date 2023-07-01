package cn.kun.demo.cache.service;

import cn.kun.demo.cache.entity.dto.ListDelDTO;
import cn.kun.demo.cache.entity.dto.ListGetByIndexDTO;
import cn.kun.demo.cache.entity.dto.ListGetDTO;
import cn.kun.demo.cache.entity.dto.ListSetDTO;
import cn.kun.demo.cache.entity.dto.ListSetIndexDTO;
import cn.kun.demo.cache.entity.dto.ListSetListDTO;
import cn.kun.demo.cache.entity.dto.ListSetListTimeDTO;
import cn.kun.demo.cache.entity.dto.ListSetTimeDTO;

import java.util.List;

/**
 * 列表-服务层
 *
 * @author 廖航
 * @date 2023-01-13 14:20
 */
public interface ListService {

    /**
     * 获取list缓存的内容
     *
     * @param key 键
     * @return 列表
     */
    List<Object> get(String key);
    
    /**
     * 取值
     * @param dto 取值-传入值
     * @return 值
     */
    List<Object> get(ListGetDTO dto);

    /**
     * 获取长度
     * @param key 键
     * @return 长度
     */
    long getSize(String key);

    /**
     * 通过索引取值
     * @param dto 通过索引取值-传入值
     * @return 值
     */
    Object getByIndex(ListGetByIndexDTO dto);

    /**
     * 设值
     * @param dto 设值-传入值
     */
    void set(ListSetDTO dto);

    /**
     * 设值并设置生效时间
     * @param dto 设值并设置生效时间-传入值
     */
    void set(ListSetTimeDTO dto);

    /**
     * 列表设值
     * @param dto 列表设值-传入值
     */
    void set(ListSetListDTO dto);

    /**
     * 列表设值并设置生效时间
     * @param dto 列表设值并设置生效时间-传入值
     */
    void set(ListSetListTimeDTO dto);

    /**
     * 通过索引设值
     * @param dto 通过索引设值-传入值
     */
    void set(ListSetIndexDTO dto);

    /**
     * 删除
     *
     * @param dto 删除-传入值
     */
    void del(ListDelDTO dto);

    /**
     * 弹出
     *
     * @param key 键
     */
    void pop(String key);
}
