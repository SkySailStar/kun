package cn.kun.base.core.global.util.check;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 校验父级工具类
 *
 * @author eric
 * @date 2023/4/24 15:53
 */
public class ParentUtils {

    /**
     * 校验上级id是否为空
     *
     * @param parentId
     * @return
     */
    public static Long checkParentId(Long parentId) {
        // 校验上级是否为空
        if (ObjUtil.isNull(parentId)) {
            return Long.parseLong("0");
        }
        return parentId;
    }

    /**
     * 校验所有上级是否为空
     *
     * @param parentIds
     * @return
     */
    public static String checkParentIds(String parentIds) {
        // 校验上级是否为空
        if (StrUtil.isBlank(parentIds)) {
            return "0,";
        }
        return parentIds;
    }
}