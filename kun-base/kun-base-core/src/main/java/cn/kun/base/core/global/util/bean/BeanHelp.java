package cn.kun.base.core.global.util.bean;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.springframework.beans.BeansException;

/**
 * 对象工具类
 *
 * @author 廖航
 * @date 2023-03-23 16:40
 */
public class BeanHelp extends BeanUtil {

    /**
     * 复制且忽略为空的属性
     * @param source 来源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        copyProperties(source, target, CopyOptions.create().setIgnoreNullValue(true));
    }

    /**
     * 复制并忽略某些字段属性
     * @param source 来源对象
     * @param target 目标对象
     * @param ignoreProperties 忽略的字段属性
     */
    public static void copyPropertiesWithExcludeProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
        copyProperties(source, target, ignoreProperties);
    }

    /**
     * 复制并忽略某些字段属性,返回目标实体
     * @param source 来源对象
     * @param tClass 目标对象class
     * @param ignoreProperties 忽略的字段属性
     */
    public static <T> T copyPropertiesWithExcludeProperties(Object source, Class<T> tClass, String... ignoreProperties) throws BeansException {
        return copyProperties(source, tClass, ignoreProperties);
    }
}
