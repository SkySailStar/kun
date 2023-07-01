package cn.kun.demo.cache.service;

import cn.kun.demo.cache.entity.dto.ExpireDTO;

/**
 * 通用-服务层
 *
 * @author 廖航
 * @date 2023-01-13 14:19
 */
public interface CommonService {
    
    /**
     * 设置数据库
     * @param dbIndex 数据库编号（默认有16个数据库，编号在0-15之间）
     */
    void setDatabase(int dbIndex);

    /**
     * 设置缓存失效时间
     * @param dto 设置缓存失效时间-传入值
     * @return 结果
     */
    boolean expire(ExpireDTO dto);

    /**
     * 获取失效时间
     * @param key 键
     * @return 失效时间
     */
    long getExpire(String key);

    /**
     * 判断key是否存在
     * @param key 键
     * @return 结果
     */
    boolean has(String key);

    /**
     * 删除缓存
     * @param key 键
     */
    void del(String key);
}
