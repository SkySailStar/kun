package cn.kun.base.core.global.util.str;

import cn.hutool.core.util.StrUtil;
import cn.kun.base.core.global.util.obj.ObjHelp;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author lujun
 */
public class StrHelp extends StrUtil {


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
                for (int i = size - len; i > 0; i--) {
                    sb.append(c);
                }
                sb.append(s);
            } else {
                return s.substring(len - size, len);
            }
        } else {
            for (int i = size; i > 0; i--) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 获取汉字字符串拼音
     *
     * @param chinese 汉字字符串
     * @return 汉字字符串拼音
     */
    public static String getFullSpell(String chinese) {

        StringBuilder pinyin = new StringBuilder();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                try {
                    pinyin.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyin.append(c);
            }
        }
        return pinyin.toString();
    }

    /**
     * 获取汉字字符串拼音首字母
     *
     * @param chinese 汉字字符串
     * @return 汉字字符串拼音首字母
     */
    public static String getFirstSpell(String chinese) {

        String firstSpell = "";
        String fullSpell = getFullSpell(chinese.substring(0, 1));
        if (ObjHelp.isNotEmpty(fullSpell)) {
            firstSpell = fullSpell.substring(0, 1);
        }
        return firstSpell;
    }

    /**
     * 判断字符串是否以英文或中文字符开头，并返回英文大写首字符或中文拼音大写首字符
     *
     * @param str 要判断的字符串
     * @return 英文大写首字符或中文拼音大写首字符，如果不是则返回#
     */
    public static String getFirstChar(String str) {
        // 正则表达式，匹配中文字符和英文字符
        String pattern = "^([a-zA-Z]|[\u4E00-\u9FA5])";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        if (m.find()) {
            String matchStr = m.group();
            // 匹配英文字母
            if (matchStr.matches("[a-zA-Z]")) {
                return matchStr.toUpperCase();
            } else {
                // 匹配中文字符
                String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(matchStr.charAt(0));
                if (pinyin != null && pinyin.length > 0) {
                    return pinyin[0].substring(0, 1).toUpperCase();
                }
            }
        }
        // 不是英文或中文字符开头，返回#
        return "#";
    }

}
