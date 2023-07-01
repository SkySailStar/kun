package cn.kun.base.core.security.util;

import cn.kun.base.core.global.util.obj.ObjHelp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 *
 * @author 廖航
 * @date 2023-06-09 14:15
 */
public class Md5Help {

    /**
     * md5加密
     * @param str 加密前字符串
     * @return 加密后字符串
     */
    public static String md5(String str) {
        
        if (ObjHelp.isEmpty(str)) {
            throw new IllegalArgumentException("加密字符为空");
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = str.getBytes();
        MessageDigest mdInst;
        try {
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        mdInst.update(btInput);
        byte[] md = mdInst.digest();
        int j = md.length;
        char[] strArr = new char[j * 2];
        int k = 0;
        for (byte byte0 : md) {
            strArr[k++] = hexDigits[byte0 >>> 4 & 0xF];
            strArr[k++] = hexDigits[byte0 & 0xF];
        }
        return new String(strArr);
    }
    
}
