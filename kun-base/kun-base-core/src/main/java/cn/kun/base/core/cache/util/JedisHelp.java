package cn.kun.base.core.cache.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.convert.ConvertHelp;
import cn.kun.base.core.global.util.obj.ObjHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Jedis-工具类（兼容老数据）
 * 
 * @author SkySailStar
 * @version 2023-06-13 18:38
 */
@Slf4j
@Component
public class JedisHelp {
	
	private static JedisPool jedisPool;
	@Autowired
	public void setJedisPool(JedisPool jedisPool) {
		JedisHelp.jedisPool = jedisPool;
	}
	
	public static final String KEY_PREFIX = "";
	
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 * @ 
	 */
	public static String get(String key)  {
		
		key = KEY_PREFIX +  key;
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.get(key);
				value = ObjHelp.isNotEmpty(value) && !"nil".equalsIgnoreCase(value) ? value : null;
			}
		} catch (Exception e) {
			log.warn("get {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 * @ 
	 */
	public static Object getObject(String key)  {
		
		Object value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = toObject(jedis.get(getBytesKey(key)));
			}
		} catch (Exception e) {
			log.warn("getObject {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 * @ 
	 */
	public static String hget(String key,String hash)  {
		
		key = KEY_PREFIX +  key;
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.hexists(hash,key)) {
				value = jedis.hget(hash,key);
				value = ObjHelp.isNotEmpty(value) && !"nil".equalsIgnoreCase(value) ? value : null;
			}
		} catch (Exception e) {
			log.warn("get {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 * @
	 */
	public static String hget(String key,String hash,int dbIndex)  {
		
		key = KEY_PREFIX +  key;
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if(dbIndex!=0){
				jedis.select(dbIndex);
			}
			if (jedis.hexists(hash,key)) {
				value = jedis.hget(hash,key);
				value = ObjHelp.isNotEmpty(value) && !"nil".equalsIgnoreCase(value) ? value : null;
			}
		} catch (Exception e) {
			log.warn("get {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 * @ 
	 */
	public static Object hgetObject(String key,String hash)  {
		
		Object value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.hexists(hash,key)) {
				value = toObject(jedis.hget(getBytesKey(hash),getBytesKey(key)));
			}
		} catch (Exception e) {
			log.warn("getObject {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 指定数据库获取缓存
	 * @param key 键
	 * @param hash 值
	 * @param dbIndex 数据库
	 * @return 值
	 * @ 
	 */
	public static Object hgetObject(String key,String hash,int dbIndex)  {
		
		Object value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if(dbIndex!=0){
				jedis.select(dbIndex);
			}
			if (jedis.hexists(hash,key)) {
				value = toObject(jedis.hget(getBytesKey(hash),getBytesKey(key)));
			}
		} catch (Exception e) {
			log.warn("getObject {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 设置缓存 持久化
	 * @param key 键
	 * @param value 值
	 * @return
	 * @ 
	 */
	public static String set(String key, String value)  {
		
		return set(key, value,0);
	}
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 * @ 
	 */
	public static String set(String key, String value, int cacheSeconds)  {
		
		key = KEY_PREFIX +  key;
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.set(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			log.warn("set {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	/**
	 * hash设置缓存
	 * @param key 键
	 * @param value 值
	 * @param hash 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return 结果
	 */
	public static Long hset(String key, String value, String hash,int cacheSeconds)  {
		
		key = KEY_PREFIX +  key;
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hset(hash,key, value);
			if (cacheSeconds != 0) {
				jedis.expire(hash, cacheSeconds);
			}
		} catch (Exception e) {
			log.warn("set {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	/**
	 * hash设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 * @ 
	 */
	public static String setObject(String key, Object value, int cacheSeconds)  {
		
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.set(getBytesKey(key), toBytes(value));
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			log.warn("setObject {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 * @ 
	 */
	public static Long hsetObject(String key, Object value,String hash,  int cacheSeconds)  {
		
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hset(getBytesKey(hash),getBytesKey(key), toBytes(value));
			if (cacheSeconds != 0) {
				jedis.expire(hash, cacheSeconds);
			}
		} catch (Exception e) {
			log.warn("setObject {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 * @ 
	 */
	public static Long hsetObject(String key, Object value,String hash,  int cacheSeconds,int dbIndex)  {
		
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if(dbIndex!=0){
				//切换使用的redis缓存库
				jedis.select(dbIndex);
			}
			result = jedis.hset(getBytesKey(hash),getBytesKey(key), toBytes(value));
			if (cacheSeconds != 0) {
				jedis.expire(hash, cacheSeconds);
			}
		} catch (Exception e) {
			log.warn("setObject {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 获取List缓存
	 * @param key 键
	 * @return 值
	 * @ 
	 */
	public static List<String> getList(String key)  {
		
		List<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.lrange(key, 0, -1);
			}
		} catch (Exception e) {
			log.warn("getList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 获取List缓存
	 * @param key 键
	 * @return 值
	 * @ 
	 */
	public static List<Object> getObjectList(String key)  {
		
		List<Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
				value = Lists.newArrayList();
				for (byte[] bs : list){
					value.add(toObject(bs));
				}
			}
		} catch (Exception e) {
			log.warn("getObjectList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 * @ 
	 */
	public static long setList(String key, List<String> value, int cacheSeconds)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.rpush(key, (String[])value.toArray());
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			log.warn("setList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 * @ 
	 */
	public static long setObjectList(String key, List<Object> value, int cacheSeconds)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			List<byte[]> list = Lists.newArrayList();
			for (Object o : value){
				list.add(toBytes(o));
			}
			result = jedis.rpush(getBytesKey(key), (byte[][])list.toArray());
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			log.warn("setObjectList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向List缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 * @ 
	 */
	public static long listAdd(String key, String... value)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.rpush(key, value);
		} catch (Exception e) {
			log.warn("listAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向List缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 * @ 
	 */
	public static long listObjectAdd(String key, Object... value)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			List<byte[]> list = Lists.newArrayList();
			for (Object o : value){
				list.add(toBytes(o));
			}
			result = jedis.rpush(getBytesKey(key), (byte[][])list.toArray());
		} catch (Exception e) {
			log.warn("listObjectAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 * @ 
	 */
	public static Set<String> getSet(String key)  {
		
		Set<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.smembers(key);
			}
		} catch (Exception e) {
			log.warn("getSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 * @ 
	 */
	public static Set<Object> getObjectSet(String key)  {
		
		Set<Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = Sets.newHashSet();
				Set<byte[]> set = jedis.smembers(getBytesKey(key));
				for (byte[] bs : set){
					value.add(toObject(bs));
				}
			}
		} catch (Exception e) {
			log.warn("getObjectSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 设置Set缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 * @ 
	 */
	public static long setSet(String key, Set<String> value, int cacheSeconds)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.sadd(key, (String[])value.toArray());
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			log.warn("setSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 设置Set缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 * @ 
	 */
	public static long setObjectSet(String key, Set<Object> value, int cacheSeconds)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			Set<byte[]> set = Sets.newHashSet();
			for (Object o : value){
				set.add(toBytes(o));
			}
			result = jedis.sadd(getBytesKey(key), (byte[][])set.toArray());
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			log.warn("setObjectSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向Set缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 * @ 
	 */
	public static long setSetAdd(String key, String... value)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.sadd(key, value);
		} catch (Exception e) {
			log.warn("setSetAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向Set缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 * @ 
	 */
	public static long setSetObjectAdd(String key, Object... value)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			Set<byte[]> set = Sets.newHashSet();
			for (Object o : value){
				set.add(toBytes(o));
			}
			result = jedis.rpush(getBytesKey(key), (byte[][])set.toArray());
		} catch (Exception e) {
			log.warn("setSetObjectAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 获取Map缓存
	 * @param key 键
	 * @return 值
	 * @ 
	 */
	public static Map<String, String> getMap(String key)  {
		
		Map<String, String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.hgetAll(key);
			}
		} catch (Exception e) {
			log.warn("getMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 获取Map缓存
	 * @param key 键
	 * @return 值
	 * @ 
	 */
	public static Map<String, Object> getObjectMap(String key)  {
		
		Map<String, Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = Maps.newHashMap();
				Map<byte[], byte[]> map = jedis.hgetAll(getBytesKey(key));
				for (Map.Entry<byte[], byte[]> e : map.entrySet()){
					value.put(ConvertHelp.toStr(e.getKey()), toObject(e.getValue()));
				}
			}
		} catch (Exception e) {
			log.warn("getObjectMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 设置Map缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 * @ 
	 */
	public static String setMap(String key, Map<String, String> value, int cacheSeconds)  {
		
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.hmset(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			log.warn("setMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 设置Map缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 * @ 
	 */
	public static String setObjectMap(String key, Map<String, Object> value, int cacheSeconds)  {
		
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			Map<byte[], byte[]> map = Maps.newHashMap();
			for (Map.Entry<String, Object> e : value.entrySet()){
				map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
			}
			result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>)map);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			log.warn("setObjectMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向Map缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 * @ 
	 */
	public static String mapPut(String key, Map<String, String> value)  {
		
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hmset(key, value);
		} catch (Exception e) {
			log.warn("mapPut {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向Map缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 * @ 
	 */
	public static String mapObjectPut(String key, Map<String, Object> value)  {
		
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			Map<byte[], byte[]> map = Maps.newHashMap();
			for (Map.Entry<String, Object> e : value.entrySet()){
				map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
			}
			result = jedis.hmset(getBytesKey(key), map);
		} catch (Exception e) {
			log.warn("mapObjectPut {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 移除Map缓存中的值
	 * @param key 键
	 * @param mapKey 值
	 * @return
	 * @ 
	 */
	public static long mapRemove(String key, String mapKey)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hdel(key, mapKey);
		} catch (Exception e) {
			log.warn("mapRemove {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 移除Map缓存中的值
	 * @param key 键
	 * @param mapKey 值
	 * @return
	 * @ 
	 */
	public static long mapObjectRemove(String key, String mapKey)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hdel(getBytesKey(key), getBytesKey(mapKey));
		} catch (Exception e) {
			log.warn("mapObjectRemove {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 判断Map缓存中的Key是否存在
	 * @param key 键
	 * @param mapKey 值
	 * @return
	 * @ 
	 */
	public static boolean mapExists(String key, String mapKey)  {
		
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hexists(key, mapKey);
		} catch (Exception e) {
			log.warn("mapExists {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 判断Map缓存中的Key是否存在
	 * @param key 键
	 * @param mapKey 值
	 * @return
	 * @ 
	 */
	public static boolean mapObjectExists(String key, String mapKey)  {
		
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hexists(getBytesKey(key), getBytesKey(mapKey));
		} catch (Exception e) {
			log.warn("mapObjectExists {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 删除缓存
	 * @param key 键
	 * @return
	 * @ 
	 */
	public static long del(String key)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)){
				result = jedis.del(key);
			}
		} catch (Exception e) {
			log.warn("del {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	/**
	 * 删除缓存
	 * @param key 键
	 * @param dbIndex 数据库索引
	 * @return
	 * @ 
	 */
	public static long del(String key,int dbIndex)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if(dbIndex!=0){
				jedis.select(dbIndex);
			}
			if (jedis.exists(key)){
				result = jedis.del(key);
			}
		} catch (Exception e) {
			log.warn("del {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * hash删除缓存
	 * @param key 键
	 * @param hash hash
	 * @return
	 * @ 
	 */
	public static long hdel(String key,String hash)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.hexists(hash,key)){
				result = jedis.hdel(hash,key);
			}
		} catch (Exception e) {
			log.warn("del {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	/**
	 * 删除缓存
	 * @param key 键
	 * @return
	 * @ 
	 */
	public static long delObject(String key)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))){
				result = jedis.del(getBytesKey(key));
			}
		} catch (Exception e) {
			log.warn("delObject {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	/**
	 * 删除缓存
	 * @param key 键
	 * @param hash 值
	 * @return
	 * @ 
	 */
	public static long hdelObject(String key,String hash)  {
		
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.hexists(getBytesKey(hash),getBytesKey(key))){
				result = jedis.hdel(getBytesKey(hash),getBytesKey(key));
			}
		} catch (Exception e) {
			log.warn("delObject {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 * @ 
	 */
	public static boolean exists(String key)  {
		
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.exists(key);
		} catch (Exception e) {
			log.warn("exists {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 * @ 
	 */
	public static boolean existsObject(String key)  {
		
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.exists(getBytesKey(key));
		} catch (Exception e) {
			log.warn("existsObject {}", key, e);
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
	public static Jedis getResource() throws JedisException, BusinessException {
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (JedisException e) {
			log.warn("getResource.", e);
			returnBrokenResource(jedis);
		}
		return jedis;
	}

	/**
	 * 归还资源
	 * @param jedis Jedis
	 */
	public static void returnBrokenResource(Jedis jedis) {
		
		if (jedis != null) {
			jedisPool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * 释放资源
	 * @param jedis Jedis
	 */
	public static void returnResource(Jedis jedis) {
		
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 获取byte[]类型Key
	 * @param object 对象
	 * @return 结果
	 */
	public static byte[] getBytesKey(Object object){
		
		if(object instanceof String str){
    		return str.getBytes();
    	}else{
    		return ObjHelp.serialize(object);
    	}
	}
	
	/**
	 * Object转换byte[]类型
	 * @param object 对象
	 * @return 结果
	 */
	public static byte[] toBytes(Object object){
		
    	return ObjHelp.serialize(object);
	}

	/**
	 * byte[]型转换Object
	 * @param bytes 字符
	 * @return 结果
	 */
	public static Object toObject(byte[] bytes){
		
		return ObjHelp.deserialize(bytes);
	}
	
	/**
	* 方法: selectDB <br>
	* 描述: 选择数据库 0 - 15 一共16个数据库. <br>
	* @param index
	 * @ 
	 */
	public static void selectDB(int index) {
		
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.select(index);
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			returnResource(jedis);
		}
	}
}
