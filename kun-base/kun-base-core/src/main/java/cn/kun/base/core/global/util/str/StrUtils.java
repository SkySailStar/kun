package cn.kun.base.core.global.util.str;

import cn.hutool.core.util.StrUtil;
import cn.kun.base.core.global.util.chinese.PinyinUtils;
import cn.kun.base.core.global.util.obj.ObjUtils;

/**
 * 字符串工具类
 *
 * @author lujun
 */
public class StrUtils extends StrUtil {

    /**
     * 字符串常量：无
     */
    public static final String WITHOUT = "无";

    /**
     * 数字左边补齐0，使之达到指定长度。注意，如果数字转换为字符串后，长度大于size，则只保留 最后size个字符。
     *
     * @param num  数字对象
     * @param size 字符串指定长度
     * @return 返回数字的字符串格式，该字符串为指定长度。
     */
    public static String padl(final Number num, final int size) {
        return padl(num.toString(), size, '0');
    }

    /**
     * 字符串左补齐。如果原始字符串s长度大于size，则只保留最后size个字符。
     *
     * @param s    原始字符串
     * @param size 字符串指定长度
     * @param c    用于补齐的字符
     * @return 返回指定长度的字符串，由原字符串左补齐或截取得到。
     */
    public static String padl(final String s, final int size, final char c) {
        final StringBuilder sb = new StringBuilder(size);
        if (s != null) {
            final int len = s.length();
            if (s.length() <= size) {
                sb.append(String.valueOf(c).repeat(size - len));
                sb.append(s);
            } else {
                return s.substring(len - size, len);
            }
        } else {
            sb.append(String.valueOf(c).repeat(Math.max(0, size)));
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否以英文或中文字符开头，并返回英文大写首字符或中文拼音大写首字符
     *
     * @param str 要判断的字符串
     * @return 英文大写首字符或中文拼音大写首字符，如果不是则返回#
     */
    public static String getFirstChar(String str) {

        if (ObjUtils.isEmpty(str)) {
            return "";
        }
        // 匹配英文字母
        if (str.substring(0, 1).matches("^[A-Za-z]+$")) {
            return str.substring(0, 1).toUpperCase();
        }
        // 匹配中文字符
        else if (str.matches("^[\\u4e00-\\u9fa5]+$")) {
            String initial = PinyinUtils.getInitial(str);
            if (ObjUtils.isNotEmpty(initial)) {
                return initial.toUpperCase();
            }
        }
        // 匹配中文字符
        else if (str.length() >= 2 && str.substring(0, 2).matches("^[\\u4e00-\\u9fa5]+$")) {
            String initial = PinyinUtils.getInitial(str);
            if (ObjUtils.isNotEmpty(initial)) {
                return initial.toUpperCase();
            }
        }
        // 不是英文或中文字符开头，返回#
        else {
            return "#";
        }
        return "";
    }

}
