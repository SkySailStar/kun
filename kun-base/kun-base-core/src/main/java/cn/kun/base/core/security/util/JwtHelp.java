package cn.kun.base.core.security.util;

import cn.hutool.core.util.IdUtil;
import cn.kun.base.core.security.constant.LoginConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * JWT工具类
 *
 * @author SkySailStar
 */
public class JwtHelp {

    /**
     * 创建
     *
     * @param subject token中要存放的数据（json格式）
     * @return jtw字符串
     */
    public static String create(String subject) {
        // 设置过期时间
        JwtBuilder builder = getBuilder(subject, null, IdUtil.simpleUUID());
        return builder.compact();
    }

    /**
     * 创建
     *
     * @param subject   token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return jtw字符串
     */
    public static String create(String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getBuilder(subject, ttlMillis, IdUtil.simpleUUID());
        return builder.compact();
    }

    /**
     * 创建
     *
     * @param id        主键
     * @param subject   主题
     * @param ttlMillis 过期时间
     * @return token字符串
     */
    public static String create(String id, String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    /**
     * 解析
     *
     * @param jwt jwt字符串
     * @return 结果
     */
    public static Claims parse(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return secretKey
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(LoginConstants.SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, LoginConstants.ALGORITHM);
    }

    /**
     * 获取JWT构建器
     * @param subject 主题
     * @param ttlMillis 生效时间
     * @param uuid 唯一ID
     * @return JWT构建器
     */
    private static JwtBuilder getBuilder(String subject, Long ttlMillis, String uuid) {
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = LoginConstants.JWT_TTL * 60 * 1000L;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                //唯一的ID
                .setId(uuid)
                // 主题（可以是JSON数据）
                .setSubject(subject)
                // 签发者
                .setIssuer(LoginConstants.SECRET)
                // 签发时间
                .setIssuedAt(now)
                // 使用HS512对称加密算法签名, 第二个参数为秘钥
                .signWith(SignatureAlgorithm.HS512, secretKey)
                // 过期时间
                .setExpiration(expDate);
    }
}