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

    /**
     * 帖子的浏览量
     */
    public static final String POST_VIEWS = "POST_VIEWS:";

    /**
     * 帖子浏览历史
     */
    public static final String POST_HISTORY = "POST_HISTORY:";

    /**
     * 帖子收藏
     */
    public static final String POST_COLLECT = "POST_COLLECT:";

    /**
     * 最后的消息
     */
    public static final String LAST_MESSAGE = "LAST_MESSAGE:";

    /**
     * 帖子帮助历史
     */
    public static final String POST_HELP = "POST_HELP:";

    /**
     * 协同推荐中每个loves值
     */
    public static final String RECOMMEND = "RECOMMEND:";

    /**
     * 协同推荐的帖子
     */
    public static final String RECOMMEND_POST = "RECOMMEND:POST:";

    /**
     * 已经推荐过的recommend的ID
     */
    public static final String RECOMMENDED_POST = "RECOMMENDED:POST:";

}
