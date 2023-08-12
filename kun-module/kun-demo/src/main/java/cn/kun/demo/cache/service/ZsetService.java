package cn.kun.demo.cache.service;

import cn.kun.demo.cache.entity.dto.ZsetDelDTO;
import cn.kun.demo.cache.entity.dto.ZsetGetDTO;
import cn.kun.demo.cache.entity.dto.ZsetIncDTO;
import cn.kun.demo.cache.entity.dto.ZsetRankDTO;
import cn.kun.demo.cache.entity.dto.ZsetSetDTO;
import cn.kun.demo.cache.entity.dto.ZsetSetSetDTO;

import java.util.Set;

/**
 * 有序集合-服务层
 *
 * @author SkySailStar
 * @date 2023-03-10 17:46
 */
public interface ZsetService {

    /**
     * 设值
     * @param dto 有序集合缓存设值-传入值
     */
    void set(ZsetSetDTO dto);

    /**
     * 插入多个元素
     * @param dto 有序集合缓存集合设值-传入值
     */
    void setSet(ZsetSetSetDTO dto);

    /**
     * 获取元素集合，从小到大排序
     * @param dto 有序集合缓存取值-传入值
     * @return 元素集合
     */
    Set<Object> get(ZsetGetDTO dto);

    /**
     * 删除元素
     * @param dto 有序集合缓存删除-传入值
     */
    void del(ZsetDelDTO dto);

    /**
     * 增加得分
     * @param dto 有序集合缓存增加得分-传入值
     * @return 增加的得分
     */
    double inc(ZsetIncDTO dto);

    /**
     * 获取排名
     * @param dto 有序集合缓存-获取排名
     * @return 排名
     */
    Long rank(ZsetRankDTO dto);

    /**
     * 获取长度
     * @param key 键
     * @return 长度
     */
    Long size(String key);
}
