package cn.kun.base.core.global.util.date;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime工具类
 *
 * @author lujun
 */
public class LocalDateTimeHelp extends LocalDateTimeUtil {

    public static DateTimeFormatter YYYY = DateTimeFormatter.ofPattern("yyyy");

    public static DateTimeFormatter YYYY_MM = DateTimeFormatter.ofPattern("yyyy-MM");

    public static DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static DateTimeFormatter YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static DateTimeFormatter YYYYMMDDHHMMSSSSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public static DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static DateTimeFormatter YYYY_MM_DD_HH_MM_SS_SSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

    /**
     * Description: 获取字符串形式的当前时间
     *
     * @param format 时间格式
     * @return 字符串形式的时间
     * @author lujun
     * @date 2022/12/15 15:04
     **/
    public static String nowStr(DateTimeFormatter format) {
        return LocalDateTimeUtil.format(LocalDateTime.now(), format);
    }

    /**
     * 格林威治时间转化为中国时间
     * @param gmt 格林威治时间
     * @return 中国时间
     */
    public static LocalDateTime castChina(LocalDateTime gmt) {
        if (ObjUtil.isNull(gmt)) {
            return null;
        }
        return gmt.plusHours(8L);
    }

}
