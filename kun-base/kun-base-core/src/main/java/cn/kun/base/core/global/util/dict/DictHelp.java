package cn.kun.base.core.global.util.dict;

import cn.hutool.core.util.StrUtil;
import cn.kun.base.core.global.constant.BaseConstants;

/**
 * 字典工具类
 *
 * @author SkySailStar
 * @date 2023-04-07 15:16
 */
public class DictHelp {

    /**
     * 转换标识（1是，0否）
     * @param flag 标识
     * @return 标识值
     */
    public static String castFlag(String flag) {
        
        String result = StrUtil.EMPTY;
        if (StrUtil.isBlank(flag)) {
            return result;
        }
        if (StrUtil.equals(flag, BaseConstants.YES)) {
            result = BaseConstants.YES_MSG;
        } else if (StrUtil.equals(flag, BaseConstants.NO)) {
            result = BaseConstants.NO_MSG;
        }
        return result;
    }

    /**
     * 转换成功标识（1成功，0失败）
     * @param flag 标识
     * @return 标识值
     */
    public static String castSuccessFlag(String flag) {

        String result = StrUtil.EMPTY;
        if (StrUtil.isBlank(flag)) {
            return result;
        }
        if (StrUtil.equals(flag, BaseConstants.YES)) {
            result = BaseConstants.SUCCESS_FLAG_MSG;
        } else if (StrUtil.equals(flag, BaseConstants.NO)) {
            result = BaseConstants.FAIL_FLAG_MSG;
        }
        return result;
    }
}
