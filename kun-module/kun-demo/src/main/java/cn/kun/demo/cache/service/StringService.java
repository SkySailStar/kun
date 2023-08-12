package cn.kun.demo.cache.service;

import cn.kun.demo.cache.entity.dto.StringIncDecDTO;
import cn.kun.demo.cache.entity.dto.StringSetDTO;
import cn.kun.demo.cache.entity.dto.StringSetIfAbsentDTO;
import cn.kun.demo.cache.entity.dto.StringSetTimeDTO;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 字符串-服务层
 *
 * @author SkySailStar
 * @date 2023-01-13 14:20
 */
public interface StringService {

    /**
     * 取值
     * @param key 键
     * @return 值
     */
    String get(@PathVariable String key);

    /**
     * 设值
     * @param dto 设值-传入值
     */
    void set(StringSetDTO dto);

    /**
     * 设值并设置生效时间
     * @param dto 设值并设置生效时间-传入值
     */
    void set(StringSetTimeDTO dto);

    /**
     * 键不存在才设值
     * @param dto 字符串-键不存在才设值-传入值
     */
    void setIfAbsent(StringSetIfAbsentDTO dto);

    /**
     * 递增
     * @param dto 字符串缓存递增递减-传入值
     * @return 递增量
     */
    long inc(StringIncDecDTO dto);

    /**
     * 递减
     * @param dto 字符串缓存递增递减-传入值
     * @return 递减量
     */
    long dec(StringIncDecDTO dto);
}
