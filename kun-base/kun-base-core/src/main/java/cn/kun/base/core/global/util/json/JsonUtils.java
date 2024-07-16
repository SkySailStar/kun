package cn.kun.base.core.global.util.json;

import cn.kun.base.core.global.util.obj.ObjUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Json-工具类
 *
 * @author 廖航
 * @date 2024-02-06 17:35
 */
public class JsonUtils {

    /**
     * Json转换成实体类
     * @param json Json字符串
     * @param tClass 目标类
     * @return 实体类
     * @param <T> 目标类
     */
    public static <T> T toEntity(String json, Class<T> tClass){

        if (ObjUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, tClass);
    }
    
    /**
     * Json转换成List
     * @param json Json字符串
     * @param tClass 目标类
     * @return list列表
     * @param <T> 目标类
     */
    public static <T> List<T> toList(String json, Class<T> tClass) {

        if (ObjUtils.isEmpty(json)) {
            return null;
        }
        List<T> result = new ArrayList<>();
        List<String> list = JSON.parseArray(json, String.class);
        for (String str : list) {
            result.add(toEntity(str, tClass));
        }
        return result;
    }

    /**
     * Json转换成List
     * @param list 源列表
     * @param tClass 目标类
     * @return 目标列表
     * @param <T> 目标类
     */
    public static <T> List<T> toList(List<String> list, Class<T> tClass) {

        if (ObjUtils.isEmpty(list)) {
            return null;
        }
        List<T> result = new ArrayList<>();
        for (String str : list) {
            // Json字符串
            if (JSON.isValid(str)) {
                // 对象
                if (str.startsWith("{") && str.endsWith("}")) {
                    result.add(toEntity(str, tClass));
                }
                // 数组
                else if (str.startsWith("[") && str.endsWith("]")) {
                    JSONArray jsonArray = JSON.parseArray(str);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        result.add(toEntity(jsonArray.getString(i), tClass));
                    }
                }
            }
            // 普通字符串
            else {
                result.add(tClass.cast(str));
            }
        }
        return result;
    }

    /**
     * Json转换成List
     * @param json Json字符串
     * @param tClass 目标类
     * @return list列表
     * @param <T> 目标类
     */
    public static <T> Set<T> toSet(String json, Class<T> tClass) {

        if (ObjUtils.isEmpty(json)) {
            return null;
        }
        Set<T> result = new HashSet<>();
        List<String> list = JSON.parseArray(json, String.class);
        for (String str : list) {
            result.add(toEntity(str, tClass));
        }
        return result;
    }

    /**
     * Json转换成List
     * @param set 源列表
     * @param tClass 目标类
     * @return 目标列表
     * @param <T> 目标类
     */
    public static <T> Set<T> toSet(Set<String> set, Class<T> tClass) {

        if (ObjUtils.isEmpty(set)) {
            return null;
        }
        Set<T> result = new HashSet<>();
        for (String str : set) {
            result.add(toEntity(str, tClass));
        }
        return result;
    }

    /**
     * Json转换成List
     * @param map 映射表
     * @param tClass 目标类
     * @return 目标列表
     * @param <T> 目标类
     */
    public static <T> Map<String, T> toMap(Map<String, String> map, Class<T> tClass) {

        if (ObjUtils.isEmpty(map)) {
            return null;
        }
        Map<String, T> result = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.put(entry.getKey(), JsonUtils.toEntity(entry.getValue(), tClass));
        }
        return result;
    }
    
}
