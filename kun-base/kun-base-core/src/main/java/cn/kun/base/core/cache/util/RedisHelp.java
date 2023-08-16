package cn.kun.base.core.cache.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 *
 * @author SkySailStar
 * @date 2023-01-04 16:01
 */
@Slf4j
@Component
public class RedisHelp {

    private static RedisTemplate<String, Object> redisTemplate;

    @Resource
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        
        RedisHelp.redisTemplate = redisTemplate;
    }
    
    /**
     * 切换数据库
     *
     * @param dbIndex 数据库编号（默认有16个数据库，编号在0-15之间）
     */
    public static void select(int dbIndex) {
        
        if (redisTemplate.getConnectionFactory() instanceof LettuceConnectionFactory connectionFactory) {
            // 设置数据库
            connectionFactory.setDatabase(dbIndex);
            // 刷新配置
            connectionFactory.afterPropertiesSet();
            // 重置连接
            connectionFactory.resetConnection();
            redisTemplate.setConnectionFactory(connectionFactory);
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间（秒），指定为-1代表永久有效
     */
    public static boolean expire(String key, long time) {
        
        return Boolean.TRUE.equals(redisTemplate.expire(key, time, TimeUnit.SECONDS));
    }

    /**
     * 根据key获取过期时间
     *
     * @param key 键
     * @return 时间（秒）。-1：永久有效；-2：该键已过期或不存在
     */
    public static long getExpire(String key) {
        
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return ObjUtil.isNull(expire) ? 0 : expire;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return 结果
     */
    public static boolean has(String key) {
        
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除缓存
     *
     * @param key 键，可以传一个或多个值
     */
    public static void del(String... key) {
        
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollUtil.toList(key));
            }
        }
    }

    /**
     * 字符串缓存取值（不能获取token值，因为其自带加密，无法解析）
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 字符串缓存设值
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key   键
     * @param value 值
     */
    public static void setIfAbsent(String key, String value) {
        
        redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 字符串缓存设值并设置生效时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间（秒），time要大于0，如果time小于等于0，将设置无限期
     */
    public static void set(String key, Object value, long time) {
        
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 递增
     *
     * @param key 键
     * @param num 要增加几
     */
    public static long inc(String key, long num) {
        
        Long increment = redisTemplate.opsForValue().increment(key, num);
        return increment == null ? 0 : increment;
    }

    /**
     * 递减
     *
     * @param key 键
     * @param num 要减少几
     */
    public static long dec(String key, long num) {
        
        Long decrement = redisTemplate.opsForValue().decrement(key, num);
        return decrement == null ? 0 : decrement;
    }

    /**
     * 获取list缓存的内容
     *
     * @param key 键
     * @return 列表
     */
    public static List<Object> getList(String key) {
        
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束。0到-1代表所有值
     */
    public static List<Object> getList(String key, long start, long end) {
        
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public static long getListSize(String key) {
        
        Long size = redisTemplate.opsForList().size(key);
        return size == null ? 0 : size;
    }

    /**
     * 获取list中的值
     *
     * @param key   键
     * @param index 索引index>=0时，0：表头，1：第二个元素，依次类推；<br>
     *              索引index<0时，-1：表尾，-2：倒数第二个元素，依次类推
     */
    public static Object getList(String key, long index) {
        
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public static void setList(String key, Object value) {
        
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存并设置生效时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间（秒）
     */
    public static void setList(String key, Object value, long time) {
        
        redisTemplate.opsForList().rightPush(key, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 将list放入列表缓存
     *
     * @param key   键
     * @param value 值
     */
    public static void setList(String key, List<Object> value) {
        
        redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 将list放入列表缓存并设置生效时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间（秒）
     */
    public static void setList(String key, List<Object> value, long time) {
        
        redisTemplate.opsForList().rightPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public static void setList(String key, long index, Object value) {
        
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除n个值为value的
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     */
    public static void delList(String key, long count, Object value) {
        
        redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 左弹出list缓存的内容
     *
     * @param key 键
     */
    public static void popList(String key) {
        
        redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    public static Set<Object> getSet(String key) {
        
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取变量中的元素
     *
     * @param key 键
     * @return value值
     */
    public static Object randomSet(String key) {
        
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public static long getSetSize(String key) {
        
        Long size = redisTemplate.opsForSet().size(key);
        return size == null ? 0 : size;
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值，可以是多个
     */
    public static void setSet(String key, Object... values) {
        
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存并设置生效时间
     *
     * @param key    键
     * @param time   时间（秒）
     * @param values 值，可以是多个
     */
    public static void setSetTime(String key, long time, Object... values) {
        
        redisTemplate.opsForSet().add(key, values);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return 结果
     */
    public static boolean hasSet(String key, Object value) {
        
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));

    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值，可以是多个
     */
    public static void delSet(String key, Object... values) {
        
        redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 添加元素，有序集合是按照元素的score值由小到大排列
     *
     * @param key   键
     * @param value 对象名称
     * @param score 数据值
     */
    public static void setZset(String key, TypedTuple<Object> value, double score) {
        
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 添加元素，向集合中插入多个元素
     *
     * @param key    键
     * @param values set集合
     */
    public static void setZset(String key, Set<TypedTuple<Object>> values) {
        
        redisTemplate.opsForZSet().add(key, values);
    }

    /*
     * 获取元素集合，从小到大排序
     * @param key
     * @param start 开始位置
     * @param end 结束位置, -1查询所有
     * @return 元素集合
     */
    public static Set<Object> getZset(String key, long start, long end) {
        
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 从集合中删除指定元素
     *
     * @param key    键
     * @param values 值
     */
    public static void delZset(String key, Object... values) {
        
        redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key   键
     * @param value 值
     * @param num   增加多少score值
     * @return 增加后的score值
     */
    public static double incScoreZset(String key, String value, double num) {
        
        Double result = redisTemplate.opsForZSet().incrementScore(key, value, num);
        return ObjUtil.isNull(result) ? 0 : result;
    }

    /**
     * 返回元素在集合的排名，有序集合是按照元素的score值由小到大排列
     *
     * @param key   键
     * @param value 值
     * @return 0表示第一位
     */
    public static long rankZset(String key, Object value) {
        
        Long result = redisTemplate.opsForZSet().rank(key, value);
        return ObjUtil.isNull(result) ? 0 : result;
    }

    /**
     * 获取集合大小
     *
     * @param key 键
     * @return 集合大小
     */
    public static long getZsetSize(String key) {
        
        Long result = redisTemplate.opsForZSet().size(key);
        return ObjUtil.isNull(result) ? 0 : result;
    }

    /**
     * 获取键对应的所有值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> getHash(String key) {
        
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 根据键和项取值
     *
     * @param key  键
     * @param item 项
     */
    public static Object getHash(String key, String item) {
        
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 设值
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public static void setHash(String key, Map<String, Object> map) {
        
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 设值
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     */
    public static void setHash(String key, Map<String, Object> map, long time) {
        
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 向一张hash表中放入数据，如果不存在则创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     */
    public static void setHash(String key, String item, Object value) {
        
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     */
    public static void setHash(String key, String item, Object value, long time) {
        
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键，不能为null
     * @param item 项，不能为null
     * @return 结果
     */
    public static boolean hasHash(String key, String item) {
        
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键，不能为null
     * @param item 项，可以是多个，不能为null
     */
    public static void delHash(String key, Object... item) {
        
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * hash递增，如果不存在，就会创建一个，并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param num  要增加几（大于0）
     */
    public static double incHash(String key, String item, double num) {
        
        return redisTemplate.opsForHash().increment(key, item, num);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param num  要减少几（小于0）
     */
    public static double decHash(String key, String item, double num) {
        
        return redisTemplate.opsForHash().increment(key, item, -num);
    }

}