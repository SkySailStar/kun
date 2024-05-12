package cn.kun.base.core.cache.util;

import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.json.JsonHelp;
import cn.kun.base.core.global.util.obj.ObjHelp;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis 工具类
 *
 * @author 廖航
 * @date 2023-01-04 16:01
 */
@Slf4j
@Component
public class RedisHelp {

    /**
     * Jedis连接池
     */
    private static JedisPool jedisPool;
    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        RedisHelp.jedisPool = jedisPool;
    }

    /**
     * 默认数据库
     */
    private static Integer defaultDatabase;
    @Value("${spring.redis.database}")
    public void setDefaultDatabase(Integer defaultDatabase) {
        RedisHelp.defaultDatabase = defaultDatabase;
    }

    /**
     * 获取匹配表达式的所有key
     * @param pattern 表达式
     * @return 匹配表达式的所有key
     */
    public static Set<String> keys(String pattern) {

        return keys(defaultDatabase, pattern);
    }

    /**
     * 获取匹配表达式的所有key
     * @param database 数据库
     * @param pattern 表达式
     * @return 匹配表达式的所有key
     */
    public static Set<String> keys(int database, String pattern) {

        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.keys(pattern);
        } catch (Exception e) {
            log.warn("keys {}", pattern, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间（秒），指定为-1代表永久有效
     */
    public static void expire(String key, long time) {

        expire(defaultDatabase, key, time);
    }

    /**
     * 指定缓存失效时间
     *
     * @param database 数据库
     * @param key  键
     * @param time 时间（秒），指定为-1代表永久有效
     */
    public static void expire(int database, String key, long time) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            if (time != 0) {
                jedis.expire(key, time);
            }
        } catch (Exception e) {
            log.warn("expire {} {}", key, time, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 根据key获取过期时间
     *
     * @param key 键
     * @return 时间（秒）。-1：永久有效；-2：该键已过期或不存在
     */
    public static long getExpire(String key) {

        return getExpire(defaultDatabase, key);
    }

    /**
     * 根据key获取过期时间
     *
     * @param database 数据库
     * @param key 键
     * @return 时间（秒）。-1：永久有效；-2：该键已过期或不存在
     */
    public static long getExpire(int database, String key) {

        long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.ttl(key);
        } catch (Exception e) {
            log.warn("ttl {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return 结果
     */
    public static boolean has(String key) {

        return has(defaultDatabase, key);
    }

    /**
     * 判断key是否存在
     *
     * @param database 数据库
     * @param key 键
     * @return 结果
     */
    public static boolean has(int database, String key) {

        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.exists(key);
        } catch (Exception e) {
            log.warn("exists {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 字符串缓存设值
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {

        set(defaultDatabase, key, value);
    }

    /**
     * 字符串缓存设值
     *
     * @param database 数据库
     * @param key   键
     * @param value 值
     */
    public static void set(int database, String key, Object value) {

        set(database, key, value, 0);
    }

    /**
     * 字符串缓存设值并设置生效时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间（秒），time要大于0，如果time小于等于0，将设置无限期
     */
    public static void set(String key, Object value, long time) {

        set(defaultDatabase, key, value, time);
    }

    /**
     * 字符串缓存设值并设置生效时间
     *
     * @param database 数据库
     * @param key   键
     * @param value 值
     * @param time  时间（秒），time要大于0，如果time小于等于0，将设置无限期
     */
    public static void set(int database, String key, Object value, long time) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.set(key, JSON.toJSONString(value));
            if (time != 0) {
                jedis.expire(key, time);
            }
        } catch (Exception e) {
            log.warn("set {} {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key   键
     * @param value 值
     */
    public static void setIfNoKey(String key, Object value) {

        setIfNoKey(defaultDatabase, key, value);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param database 数据库
     * @param key   键
     * @param value 值
     */
    public static void setIfNoKey(int database, String key, Object value) {

        setIfNoKey(database, key, value, 0);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key   键
     * @param value 值
     */
    public static void setIfNoKey(String key, Object value, long time) {

        setIfNoKey(defaultDatabase, key, value, time);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param database 数据库
     * @param key   键
     * @param value 值
     */
    public static void setIfNoKey(int database, String key, Object value, long time) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.setnx(key, JSON.toJSONString(value));
            if (time != 0) {
                jedis.expire(key, time);
            }
        } catch (Exception e) {
            log.warn("setnx {} {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 递增
     *
     * @param key 键
     */
    public static long inc(String key) {

        return inc(defaultDatabase, key);
    }

    /**
     * 递增
     *
     * @param database 数据库
     * @param key 键
     */
    public static long inc(int database, String key) {

        long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.incr(key);
        } catch (Exception e) {
            log.warn("incr {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 递增
     *
     * @param key 键
     * @param num 要增加几
     * @return 操作后结果
     */
    public static long inc(String key, long num) {

        return inc(defaultDatabase, key, num);
    }

    /**
     * 递增
     *
     * @param database 数据库
     * @param key 键
     * @param num 要增加几
     * @return 操作后结果
     */
    public static long inc(int database, String key, long num) {

        long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            if (num != 0) {
                result = jedis.incrBy(key, num);
            }
        } catch (Exception e) {
            log.warn("incrBy {} {}", key, num, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 递减
     *
     * @param key 键
     * @return 操作后结果
     */
    public static long dec(String key) {

        return dec(defaultDatabase, key);
    }

    /**
     * 递减
     *
     * @param database 数据库
     * @param key 键
     * @return 操作后结果
     */
    public static long dec(int database, String key) {

        long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.decr(key);
        } catch (Exception e) {
            log.warn("decr {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 递减
     *
     * @param key 键
     * @param num 要减少几
     * @return 操作后结果
     */
    public static long dec(String key, long num) {

        return dec(defaultDatabase, key, num);
    }

    /**
     * 递减
     *
     * @param database 数据库
     * @param key 键
     * @param num 要减少几
     * @return 操作后结果
     */
    public static long dec(int database, String key, long num) {

        long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            if (num != 0) {
                result = jedis.decrBy(key, num);
            }
        } catch (Exception e) {
            log.warn("decrBy {} {}", key, num, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public static void del(String key) {

        del(defaultDatabase, key);
    }
    
    /**
     * 删除缓存
     *
     * @param database 数据库
     * @param key 键
     */
    public static void del(int database, String key) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.del(key);
        } catch (Exception e) {
            log.warn("del {}", key, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 删除缓存
     *
     * @param keyList 键列表
     */
    public static void del(List<String> keyList) {

        del(defaultDatabase, keyList);
    }
    
    /**
     * 删除缓存
     *
     * @param database 数据库
     * @param keyList 键列表
     */
    public static void del(int database, List<String> keyList) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.del(keyList.toArray(new String[0]));
        } catch (Exception e) {
            log.warn("del {}", keyList, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 字符串缓存取值（不能获取token值，因为其自带加密，无法解析）
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {

        return get(defaultDatabase, key);
    }

    /**
     * 字符串缓存取值（不能获取token值，因为其自带加密，无法解析）
     *
     * @param database 数据库
     * @param key 键
     * @return 值
     */
    public static String get(int database, String key) {

        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.get(key);
        } catch (Exception e) {
            log.warn("get {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }
    
    /**
     * 字符串缓存取值（不能获取token值，因为其自带加密，无法解析）
     *
     * @param key 键
     * @param tClass 目标类
     * @return 值
     */
    public static <T> T get(String key, Class<T> tClass) {
        
        return get(defaultDatabase, key, tClass);
    }

    /**
     * 字符串缓存取值（不能获取token值，因为其自带加密，无法解析）
     *
     * @param database 数据库
     * @param key 键
     * @param tClass 目标类
     * @return 值
     */
    public static <T> T get(int database, String key, Class<T> tClass) {

        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.get(key);
        } catch (Exception e) {
            log.warn("get {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return JsonHelp.toEntity(result, tClass);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public static void setList(String key, Object value) {

        setList(defaultDatabase, key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param database 数据库
     * @param key   键
     * @param value 值
     */
    public static void setList(int database, String key, Object value) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.rpush(key, JSON.toJSONString(value));
        } catch (Exception e) {
            log.warn("rpush {} {}", key, value, e);
        } finally {
            returnResource(jedis);
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

        setList(defaultDatabase, key, index, value);
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param database 数据库
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public static void setList(int database, String key, long index, Object value) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.lset(key, index, JSON.toJSONString(value));
        } catch (Exception e) {
            log.warn("lset {} {} {}", key, index, value, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 移除n个值为value的
     *
     * @param key   键
     * @param value 值
     * @param count 移除多少个
     */
    public static void delList(String key, Object value, long count) {

        delList(defaultDatabase, key, value, count);
    }

    /**
     * 移除n个值为value的
     *
     * @param database 数据库
     * @param key   键
     * @param value 值
     * @param count 移除多少个
     */
    public static void delList(int database, String key, Object value, long count) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.lrem(key, count, JSON.toJSONString(value));
        } catch (Exception e) {
            log.warn("lrem {} {} {}", key, count, value, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 右弹出list缓存的内容
     *
     * @param key 键
     */
    public static void popList(String key) {

        popList(defaultDatabase, key);
    }

    /**
     * 右弹出list缓存的内容
     *
     * @param database 数据库
     * @param key 键
     */
    public static void popList(int database, String key) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.rpop(key);
        } catch (Exception e) {
            log.warn("rpop {}", key, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 获取list缓存的内容
     *
     * @param key 键
     * @return 列表
     */
    public static String getList(String key) {

        return getList(defaultDatabase, key);
    }
    
    /**
     * 获取list缓存的内容
     *
     * @param database 数据库
     * @param key 键
     * @return 列表
     */
    public static String getList(int database, String key) {

        return getList(database, key, 0, -1);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束。0到-1代表所有值
     */
    public static String getList(String key, long start, long end) {

        return getList(defaultDatabase, key, start, end);
    }
    
    /**
     * 获取list缓存的内容
     *
     * @param database 数据库
     * @param key   键
     * @param start 开始
     * @param end   结束。0到-1代表所有值
     */
    public static String getList(int database, String key, long start, long end) {

        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.lrange(key, start, end);
        } catch (Exception e) {
            log.warn("lrange {} {} {}", key, start, end, e);
        } finally {
            returnResource(jedis);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key 键
     * @param tClass 目标类
     * @return 列表
     */
    public static <T> List<T> getList(String key, Class<T> tClass) {

        return getList(defaultDatabase, key, tClass);
    }

    /**
     * 获取list缓存的内容
     *
     * @param database 数据库
     * @param key 键
     * @param tClass 目标类
     * @return 列表
     */
    public static <T> List<T> getList(int database, String key, Class<T> tClass) {

        return getList(database, key, 0, -1, tClass);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束。0到-1代表所有值
     * @param tClass 目标类
     */
    public static <T> List<T> getList(String key, long start, long end, Class<T> tClass) {

        return getList(defaultDatabase, key, start, end, tClass);
    }

    /**
     * 获取list缓存的内容
     *
     * @param database 数据库
     * @param key   键
     * @param start 开始
     * @param end   结束。0到-1代表所有值
     * @param tClass 目标类
     */
    public static <T> List<T> getList(int database, String key, long start, long end, Class<T> tClass) {

        List<T> result = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = JsonHelp.toList(jedis.lrange(key, start, end), tClass);
        } catch (Exception e) {
            log.warn("lrange {} {} {}", key, start, end, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取list中的值
     *
     * @param key   键
     * @param index 索引index>=0时，0：表头，1：第二个元素，依次类推；<br>
     *              索引index<0时，-1：表尾，-2：倒数第二个元素，依次类推
     */
    public static String getList(String key, long index) {

        return getList(defaultDatabase, key, index);
    }
    
    /**
     * 获取list中的值
     *
     * @param database 数据库
     * @param key   键
     * @param index 索引index>=0时，0：表头，1：第二个元素，依次类推；<br>
     *              索引index<0时，-1：表尾，-2：倒数第二个元素，依次类推
     */
    public static String getList(int database, String key, long index) {

        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.lindex(key, index);
        } catch (Exception e) {
            log.warn("lindex {} {}", key, index, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取list中的值
     *
     * @param key   键
     * @param index 索引index>=0时，0：表头，1：第二个元素，依次类推；<br>
     *              索引index<0时，-1：表尾，-2：倒数第二个元素，依次类推
     * @param tClass 目标类
     * * @return 值
     */
    public static <T> T getList(String key, long index, Class<T> tClass) {

        return getList(defaultDatabase, key, index, tClass);
    }

    /**
     * 获取list中的值
     *
     * @param database 数据库
     * @param key   键
     * @param index 索引index>=0时，0：表头，1：第二个元素，依次类推；<br>
     *              索引index<0时，-1：表尾，-2：倒数第二个元素，依次类推
     * @param tClass 目标类
     * @return 值
     */
    public static <T> T getList(int database, String key, long index, Class<T> tClass) {

        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.lindex(key, index);
        } catch (Exception e) {
            log.warn("lindex {} {}", key, index, e);
        } finally {
            returnResource(jedis);
        }
        return JsonHelp.toEntity(result, tClass);
    }
    
    

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public static long getListSize(String key) {

        return getListSize(defaultDatabase, key);
    }
    
    /**
     * 获取list缓存的长度
     *
     * @param database 数据库
     * @param key 键
     */
    public static long getListSize(int database, String key) {

        long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            if (has(key)) {
                result = jedis.llen(key);
            }
        } catch (Exception e) {
            log.warn("llen {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param value 值
     */
    public static void setSet(String key, Object value) {

        setSet(defaultDatabase, key, value);
    }

    /**
     * 将数据放入set缓存
     *
     * @param database 数据库
     * @param key    键
     * @param value 值
     */
    public static void setSet(int database, String key, Object value) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.sadd(key, JSON.toJSONString(value));
        } catch (Exception e) {
            log.warn("sadd {} {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param value 值
     */
    public static void delSet(String key, Object value) {

        delSet(defaultDatabase, key, value);
    }

    /**
     * 移除值为value的
     *
     * @param database 数据库
     * @param key    键
     * @param value 值
     */
    public static void delSet(int database, String key, Object value) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.srem(key, JSON.toJSONString(value));
        } catch (Exception e) {
            log.warn("srem {} {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return 列表
     */
    public static Set<String> getSet(String key) {

        return getSet(defaultDatabase, key);
    }
    
    /**
     * 根据key获取Set中的所有值
     *
     * @param database 数据库
     * @param key 键
     * @return 列表
     */
    public static Set<String> getSet(int database, String key) {

        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.smembers(key);
        } catch (Exception e) {
            log.warn("smembers {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @param tClass 目标类
     * @return 列表
     */
    public static <T> Set<T> getSet(String key, Class<T> tClass) {

        return getSet(defaultDatabase, key, tClass);
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param database 数据库
     * @param key 键
     * @param tClass 目标类
     * @return 列表
     */
    public static <T> Set<T> getSet(int database, String key, Class<T> tClass) {

        Set<T> result = new HashSet<>();
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = JsonHelp.toSet(jedis.smembers(key), tClass);
        } catch (Exception e) {
            log.warn("smembers {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 随机获取变量中的元素
     *
     * @param key 键
     * @return value值
     */
    public static String randomSet(String key) {

        return randomSet(defaultDatabase, key);
    }
    
    /**
     * 随机获取变量中的元素
     *
     * @param database 数据库
     * @param key 键
     * @return value值
     */
    public static String randomSet(int database, String key) {

        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.srandmember(key);
        } catch (Exception e) {
            log.warn("srandmember {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 随机获取变量中的元素
     *
     * @param key 键
     * @param tClass 目标类
     * @return value值
     */
    public static <T> T randomSet(String key, Class<T> tClass) {

        return randomSet(defaultDatabase, key, tClass);
    }

    /**
     * 随机获取变量中的元素
     *
     * @param database 数据库
     * @param key 键
     * @param tClass 目标类
     * @return value值
     */
    public static <T> T randomSet(int database, String key, Class<T> tClass) {

        T result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = JsonHelp.toEntity(jedis.srandmember(key), tClass);
        } catch (Exception e) {
            log.warn("srandmember {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public static long getSetSize(String key) {

        return getSetSize(defaultDatabase, key);
    }
    
    /**
     * 获取set缓存的长度
     *
     * @param database 数据库
     * @param key 键
     */
    public static long getSetSize(int database, String key) {

        long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.scard(key);
        } catch (Exception e) {
            log.warn("scard {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return 结果
     */
    public static boolean hasSet(String key, Object value) {

        return hasSet(defaultDatabase, key, value);
    }
    
    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param database 数据库
     * @param key   键
     * @param value 值
     * @return 结果
     */
    public static boolean hasSet(int database, String key, Object value) {

        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.sismember(key, JSON.toJSONString(value));
        } catch (Exception e) {
            log.warn("sismember {} {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设值
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public static void setHash(String key, Map<String, Object> map) {

        setHash(defaultDatabase, key, map);
    }

    /**
     * 设值
     *
     * @param database 数据库
     * @param key 键
     * @param map 对应多个键值
     */
    public static void setHash(int database, String key, Map<String, Object> map) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            Map<String, String> newMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String value) {
                    newMap.put(entry.getKey(), value);
                } else {
                    newMap.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
                }
            }
            jedis.hmset(key, newMap);
        } catch (Exception e) {
            log.warn("hmset {} = {}", key, map, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 向一张hash表中放入数据，如果不存在则创建
     *
     * @param key   键
     * @param field  项
     * @param value 值
     */
    public static void setHash(String key, String field, Object value) {

        setHash(defaultDatabase, key, field, value);
    }

    /**
     * 向一张hash表中放入数据，如果不存在则创建
     *
     * @param database 数据库
     * @param key   键
     * @param field  项
     * @param value 值
     */
    public static void setHash(int database, String key, String field, Object value) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.hset(key, field, JSON.toJSONString(value));
        } catch (Exception e) {
            log.warn("hset {} {} {}", key, field, value, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * hash递增，如果不存在，就会创建一个，并把新增后的值返回
     *
     * @param key  键
     * @param field 项
     * @param num  要增加几（大于0）
     */
    public static long incHash(String key, String field, long num) {

        return incHash(defaultDatabase, key, field, num);
    }

    /**
     * hash递增，如果不存在，就会创建一个，并把新增后的值返回
     *
     * @param database 数据库
     * @param key  键
     * @param field 项
     * @param num  要增加几（大于0）
     */
    public static long incHash(int database, String key, String field, long num) {

        long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.hincrBy(key, field, num);
        } catch (Exception e) {
            log.warn("hincrBy {} {} {}", key, field, num, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param field 项
     * @param num  要减少几（小于0）
     */
    public static long decHash(String key, String field, long num) {

        return decHash(defaultDatabase, key, field, num);
    }

    /**
     * hash递减
     *
     * @param database 数据库
     * @param key  键
     * @param field 项
     * @param num  要减少几（小于0）
     */
    public static long decHash(int database, String key, String field, long num) {

        long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.hincrBy(key, field, -num);
        } catch (Exception e) {
            log.warn("hincrBy {} {} {}", key, field, -num, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键，不能为null
     * @param field 字段
     */
    public static void delHash(String key, String field) {

        delHash(defaultDatabase, key, field);
    }

    /**
     * 删除hash表中的值
     *
     * @param database 数据库
     * @param key  键，不能为null
     * @param field 字段
     */
    public static void delHash(int database, String key, String field) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.hdel(key, field);
        } catch (Exception e) {
            log.warn("hdel {} {}", key, field, e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键，不能为null
     * @param fieldList 字段列表
     */
    public static void delHash(String key, List<String> fieldList) {

        delHash(defaultDatabase, key, fieldList);
    }

    /**
     * 删除hash表中的值
     *
     * @param database 数据库
     * @param key  键，不能为null
     * @param fieldList 字段列表
     */
    public static void delHash(int database, String key, List<String> fieldList) {

        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            jedis.hdel(key, fieldList.toArray(new String[0]));
        } catch (Exception e) {
            log.warn("hdel {} {}", key, fieldList, e);
        } finally {
            returnResource(jedis);
        }
    }
    
    /**
     * 获取键对应的所有值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<String, String> getHash(String key) {

        return getHash(defaultDatabase, key);
    }
    
    /**
     * 获取键对应的所有值
     *
     * @param database 数据库
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<String, String> getHash(int database, String key) {

        Map<String, String> result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.hgetAll(key);
        } catch (Exception e) {
            log.warn("hgetAll {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取键对应的所有值
     *
     * @param key 键
     * @param tClass 目标类
     * @return 对应的多个键值
     */
    public static <T> Map<String, T> getHash(String key, Class<T> tClass) {

        return getHash(defaultDatabase, key, tClass);
    }

    /**
     * 获取键对应的所有值
     *
     * @param database 数据库
     * @param key 键
     * @param tClass 目标类
     * @return 对应的多个键值
     */
    public static <T> Map<String, T> getHash(int database, String key, Class<T> tClass) {

        Map<String, T> result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = JsonHelp.toMap(jedis.hgetAll(key), tClass);
        } catch (Exception e) {
            log.warn("hgetAll {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 根据键和项取值
     *
     * @param key  键
     * @param field 项
     */
    public static String getHash(String key, String field) {

        return getHash(defaultDatabase, key, field);
    }
    
    /**
     * 根据键和项取值
     *
     * @param database 数据库
     * @param key  键
     * @param field 项
     */
    public static String getHash(int database, String key, String field) {

        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.hget(key, field);
        } catch (Exception e) {
            log.warn("hget {} {}", key, field, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 根据键和项取值
     *
     * @param key  键
     * @param field 项
     */
    public static Object getHashObject(String key, String field) {

        return getHashObject(defaultDatabase, key, field);
    }

    /**
     * 根据键和项取值
     *
     * @param database 数据库
     * @param key  键
     * @param field 项
     */
    public static Object getHashObject(int database, String key, String field) {

        Object result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            byte[] bytes = jedis.hget(ser(key), ser(field));
            if (ObjHelp.isNotEmpty(bytes)) {
                result = deSer(bytes);
            }
        } catch (Exception e) {
            log.warn("hget {} {}", key, field, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 根据键和项取值
     *
     * @param key  键
     * @param field 项
     * @param tClass 目标类
     * @return 结果
     */
    public static <T> List<T> getHashList(String key, String field, Class<T> tClass) {

        return getHashList(defaultDatabase, key, field, tClass);
    }

    /**
     * 根据键和项取值
     *
     * @param database 数据库
     * @param key  键
     * @param field 项
     */
    public static <T> List<T> getHashList(int database, String key, String field, Class<T> tClass) {

        List<T> result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = JsonHelp.toList(jedis.hget(key, field), tClass);
        } catch (Exception e) {
            log.warn("hget {} {}", key, field, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 根据键和项取值
     *
     * @param key  键
     * @param field 项
     * @param tClass 目标类
     * @return 结果
     */
    public static <T> List<T> getHashList(String key, List<String> field, Class<T> tClass) {

        return getHashList(defaultDatabase, key, field, tClass);
    }

    /**
     * 根据键和项取值
     *
     * @param database 数据库
     * @param key  键
     * @param field 项
     */
    public static <T> List<T> getHashList(int database, String key, List<String> field, Class<T> tClass) {

        List<T> result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = JsonHelp.toList(jedis.hmget(key, field.toArray(new String[0])), tClass);
        } catch (Exception e) {
            log.warn("hget {} {}", key, field, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 根据键和项取值
     *
     * @param key  键
     * @param field 项
     * @param tClass 目标类
     * @return 结果
     */
    public static <T> Set<T> getHashSet(String key, String field, Class<T> tClass) {

        return getHashSet(defaultDatabase, key, field, tClass);
    }

    /**
     * 根据键和项取值
     *
     * @param database 数据库
     * @param key  键
     * @param field 项
     */
    public static <T> Set<T> getHashSet(int database, String key, String field, Class<T> tClass) {

        Set<T> result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = JsonHelp.toSet(jedis.hget(key, field), tClass);
        } catch (Exception e) {
            log.warn("hget {} {}", key, field, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }
    
    /**
     * 根据键和项取值
     *
     * @param key  键
     * @param field 项
     * @param tClass 目标类
     * @return 结果
     */
    public static <T> T getHash(String key, String field, Class<T> tClass) {

        return getHash(defaultDatabase, key, field, tClass);
    }

    /**
     * 根据键和项取值
     *
     * @param database 数据库
     * @param key  键
     * @param field 项
     */
    public static <T> T getHash(int database, String key, String field, Class<T> tClass) {

        T result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = JsonHelp.toEntity(jedis.hget(key, field), tClass);
        } catch (Exception e) {
            log.warn("hget {} {}", key, field, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取键对应的所有项值
     *
     * @param key 键
     * @return 键对应的所有项值
     */
    public static Set<String> getHashFields(String key) {

        return getHashFields(defaultDatabase, key);
    }
    
    /**
     * 获取键对应的所有项值
     *
     * @param database 数据库
     * @param key 键
     * @return 键对应的所有项值
     */
    public static Set<String> getHashFields(int database, String key) {

        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.hkeys(key);
        } catch (Exception e) {
            log.warn("hkeys {}", key, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键，不能为null
     * @param field 项，不能为null
     * @return 结果
     */
    public static boolean hasHash(String key, String field) {

        return hasHash(defaultDatabase, key, field);
    }
    
    /**
     * 判断hash表中是否有该项的值
     *
     * @param database 数据库
     * @param key  键，不能为null
     * @param field 项，不能为null
     * @return 结果
     */
    public static boolean hasHash(int database, String key, String field) {

        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (database != defaultDatabase) {
                jedis.select(database);
            }
            result = jedis.hexists(key, field);
        } catch (Exception e) {
            log.warn("hexists {} {}", key, field, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取资源
     * @return Jedis
     * @throws JedisException
     * @ 
     */
    private static Jedis getResource() throws JedisException, BusinessException {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (JedisException e) {
            log.warn("获取资源：", e);
            returnBrokenResource(jedis);
        }
        return jedis;
    }

    /**
     * 归还资源
     * @param jedis Jedis
     */
    private static void returnBrokenResource(Jedis jedis) {

        if (jedis != null) {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    /**
     * 释放资源
     * @param jedis Jedis
     */
    private static void returnResource(Jedis jedis) {

        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 序列化
     * @param obj 对象
     * @return 结果
     */
    private static byte[] ser(Object obj){

        if(obj instanceof String str){
            return str.getBytes();
        }else{
            return ObjHelp.serialize(obj);
        }
    }

    /**
     * 反序列化
     * @param bytes 字符
     * @return 结果
     */
    private static Object deSer(byte[] bytes){

        return ObjHelp.deserialize(bytes);
    }

}