package com.ateh.eh.common;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: RedisConstants.java
 *
 * @author huang.yijie
 * 时间: 2023/3/19 21:18
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
public class RedisConstants {

    /**
     * 用户TOKEN
     */
    public static final String LOGIN_KEY = "LOGIN_KEY:";

    /**
     * Token有效期（30分钟）
     */
    public static final Long TOKEN_VALID_TIME = 30 * 60 * 1000L;

    /**
     * 邮箱验证码
     */
    public static final String EMAIL_VERIFICATION_CODE = "EMAIL_VERIFICATION_CODE:";

    /**
     * 邮箱验证码有效期（5分钟）
     */
    public static final Long EMAIL_CODE_VALID_TIME = 5 * 60L;

    /**
     * 粉丝列表key
     */
    public static final String FOLLOWS = "FOLLOWS:";

    /**
     * 关注列表key
     */
    public static final String CONCERNS = "CONCERNS:";
}
