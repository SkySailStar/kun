package cn.kun.base.core.global.util.obj;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.exception.BusinessException;

import java.lang.reflect.Field;

/**
 * 对象属性判空工具类
 *
 * @author kuangjc
 */
public class ObjHelp extends ObjUtil {
    
    /**
     * 判断实体对象及其属性是否为空
     *
     * @param object entity对象
     * @return boolean
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        // 如果对象为null直接返回true
        if (null == object) {
            return true;
        }
        try {
            // 挨个获取对象属性值
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                // 如果属性名不为serialVersionUID，有一个属性值不为null，且值不是空字符串，就返回false
                if (!"serialVersionUID".equals(f.getName()) &&
                        f.get(object) != null && StrUtil.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 如果对象为空则抛出异常
     *
     * @param obj 对象
     */
    public static void isEmptyException(Object obj) {

        if (isEmpty(obj)) {
            throw new BusinessException(ErrorCodeConstants.NULL, "对象为空");
        }
    }

    /**
     * 如果对象为空则抛出异常
     *
     * @param obj 对象
     * @param message 异常信息
     */
    public static void isEmptyException(Object obj, String message) {

        if (isEmpty(obj)) {
            throw new BusinessException(ErrorCodeConstants.NULL, message);
        }
    }

    /**
     * 判断对象是否不为空
     * @param obj 对象
     * @return 结果
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}
