package cn.kun.demo.cache.service;

import cn.kun.demo.cache.entity.dto.HashDelDTO;
import cn.kun.demo.cache.entity.dto.HashGetByKeyValueDTO;
import cn.kun.demo.cache.entity.dto.HashHasDTO;
import cn.kun.demo.cache.entity.dto.HashIncDecDTO;
import cn.kun.demo.cache.entity.dto.HashSetDTO;
import cn.kun.demo.cache.entity.dto.HashSetMapDTO;
import cn.kun.demo.cache.entity.dto.HashSetMapTimeDTO;
import cn.kun.demo.cache.entity.dto.HashSetTimeDTO;
import cn.kun.demo.cache.entity.dto.HashSetValueDTO;
import cn.kun.demo.cache.entity.dto.HashSetValueTimeDTO;

import java.util.Map;

/**
 * 哈希-服务层
 *
 * @author SkySailStar
 * @date 2023-01-13 14:21
 */
public interface HashService {

    /**
     * 获取键对应的所有值
     * @param key 键
     * @return 键对应的所有值
     */
    Map<Object, Object> get(String key);

    /**
     * 根据键和项取值
     * @param dto 哈希缓存根据键和项取值-传入值
     * @return 值
     */
    Object getByKeyValue(HashGetByKeyValueDTO dto);

    /**
     * 设值
     * @param dto 哈希缓存设值-传入值
     */
    void set(HashSetDTO dto);

    /**
     * 设值并设值生效时间
     * @param dto 哈希缓存设值并设值生效时间-传入值
     */
    void set(HashSetTimeDTO dto);
    
    /**
     * 设值
     * @param dto 哈希缓存设值-传入值
     */
    void set(HashSetMapDTO dto);

    /**
     * 设值并设值生效时间
     * @param dto 哈希缓存设值并设值生效时间-传入值
     */
    void set(HashSetMapTimeDTO dto);

    /**
     * 设值若不存在则创建
     * @param dto 哈希缓存设值若不存在则创建-传入值
     */
    void set(HashSetValueDTO dto);

    /**
     * 设值若不存在则创建并设置生效时间
     * @param dto 哈希缓存设值若不存在则创建并设值生效时间-传入值
     */
    void set(HashSetValueTimeDTO dto);

    /**
     * 判断键和项是否存在
     * @param dto 哈希缓存判断键和项是否存在-传入值
     * @return 结果
     */
    boolean has(HashHasDTO dto);

    /**
     * 删除
     * @param dto 哈希缓存删除-传入值
     */
    void del(HashDelDTO dto);

    /**
     * 递增
     * @param dto 哈希缓存递增递减-传入值
     * @return 递增值
     */
    double inc(HashIncDecDTO dto);

    /**
     * 递减
     * @param dto 哈希缓存递增递减-传入值
     * @return 递减值
     */
    double dec(HashIncDecDTO dto);
}
