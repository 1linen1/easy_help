package com.ateh.eh.common;

/**
 * <p>
 * 类说明：通用常量
 * <p>
 * 类名称: CommonConstants.java
 *
 * @author huang.yijie
 * 时间: 2023/3/19 20:44
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
public class CommonConstants {

    /**
     * 状态无效值 00X
     */
    public static final String STATUS_INVALID = "00X";

    /**
     * 状态有效值 00A
     */
    public static final String STATUS_VALID = "00A";

    /**
     * 帖子已解决状态 00R
     */
    public static final String POST_RESOLVED = "00R";

    /**
     * 帖子未解决状态 00U
     */
    public static final String POST_UNRESOLVED = "00U";

    /**
     * 举报成功状态 00B
     */
    public static final String WARNING_SUCCESS = "00B";

    /**
     * 举报失败状态 00C
     */
    public static final String WARNING_FAIL = "00C";

    /**
     * 申诉成功状态 00D
     */
    public static final String APPEAL_SUCCESS = "00D";

    /**
     * 申诉失败状态 00E
     */
    public static final String APPEAL_FAIL = "00E";

    /**
     * 普通用户
     */
    public static final String NORMAL_USER_2 = "2";

    /**
     * 管理员
     */
    public static final String ADMIN_USER_1 = "1";

    /**
     * 动态 1
     */
    public static final String POST_TYPE_DYNAMIC = "1";

    /**
     * 求助帖 0
     */
    public static final String POST_TYPE_HELP = "0";

    /**
     * 未读 0
     */
    public static final String IS_READ_FALSE = "0";

    /**
     * 已读 1
     */
    public static final String IS_READ_TRUE = "1";
}
