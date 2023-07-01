package cn.kun.demo.cache.service;

import cn.kun.demo.cache.entity.dto.SetDelDTO;
import cn.kun.demo.cache.entity.dto.SetHashDTO;
import cn.kun.demo.cache.entity.dto.SetSetDTO;
import cn.kun.demo.cache.entity.dto.SetSetTimeDTO;

import java.util.Set;

/**
 * 集合-服务层
 *
 * @author 廖航
 * @date 2023-01-13 14:20
 */
public interface SetService {

    /**
     * 取值
     * @param key 键
     * @return 值
     */
    Set<Object> get(String key);

    /**
     * 随机获取变量中的元素
     * @param key 键
     * @return 元素
     */
    Object randomSet(String key);

    /**
     * 获取长度
     * @param key 键
     * @return 长度
     */
    long getSize(String key);

    /**
     * 设值
     * @param dto 集合缓存设值-传入值
     */
    void set(SetSetDTO dto);

    /**
     * 设值并设值生效时间
     * @param dto 集合缓存设值并设值生效时间-传入值
     */
    void set(SetSetTimeDTO dto);

    /**
     * 根据值查询
     * @param dto 集合缓存是否存在值-传入值
     * @return 结果
     */
    boolean has(SetHashDTO dto);

    /**
     * 删除
     * @param dto 集合缓存删除-传入值
     */
    void del(SetDelDTO dto);
}
