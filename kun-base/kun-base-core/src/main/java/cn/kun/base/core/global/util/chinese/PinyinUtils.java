package cn.kun.base.core.global.util.chinese;

import com.hankcs.hanlp.dictionary.py.PinyinDictionary;
import cn.kun.base.core.global.util.obj.ObjUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class PinyinUtils {
 
    /**
     * 获取中文完整拼音
     *
     * @param chineseStr 中文字符串
     * @return 中文拼音
     */
    public static String getPinyin(String chineseStr) {
        List<com.hankcs.hanlp.dictionary.py.Pinyin> pinyins = PinyinDictionary.convertToPinyin(chineseStr);
        StringBuilder stringBuilder = new StringBuilder();
        for (com.hankcs.hanlp.dictionary.py.Pinyin pinyin : pinyins) {
            stringBuilder.append(pinyin.getPinyinWithoutTone());
        }
        return stringBuilder.toString();
    }
 
 
    /**
     * 获取中文拼音首字母
     *
     * @param chineseStr 中文字符串
     * @return 中文拼音首字母
     */
    public static String getInitial(String chineseStr) {
        List<com.hankcs.hanlp.dictionary.py.Pinyin> pinyins = PinyinDictionary.convertToPinyin(chineseStr);
        if (ObjUtils.isEmpty(pinyins)) {
            return StringUtils.EMPTY;
        }
        return String.valueOf(pinyins.get(0).getPinyinWithoutTone().charAt(0));
    }
}