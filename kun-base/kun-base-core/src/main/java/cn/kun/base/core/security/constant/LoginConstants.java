package cn.kun.base.core.security.constant;

/**
 * 认证常量
 *
 * @author 天航星
 */
public class LoginConstants {

    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "Authorization";

    /**
     * oAuth2.0的token前缀
     */
    public static final String BEARER = "Bearer ";

    /**
     * 令牌秘钥
     */
    public final static String SECRET = "kun";

    /**
     * 算法
     */
    public static final String ALGORITHM = "AES";

    /**
     * 有效期（单位：分钟）
     */
    public static final Long JWT_TTL = 3 * 24 * 60L;

    /**
     * 登录接口路径
     */
    public static final String LOGIN_PATH = "/auth/login";

}
