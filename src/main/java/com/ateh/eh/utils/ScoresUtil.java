package com.ateh.eh.utils;

import cn.hutool.core.date.DateUtil;
import com.ateh.eh.entity.Post;

import java.util.Date;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: ScoresUtil.java
 *
 * @author huang.yijie
 * 时间: 2023/5/11 13:38
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
public class ScoresUtil {

    public static Long getPostValidPost(Post post) {
        long day = DateUtil.betweenDay(post.getCreateDate(), new Date(), false);
        long basicScores;
        if (day <= 1) {
            basicScores =  50L;
        } else if (day <= 2) {
            basicScores = 75L;
        } else if (day <= 3) {
            basicScores = 100L;
        } else if (day <= 4) {
            basicScores = 150L;
        } else {
            basicScores = 250L;
        }
        return basicScores + post.getScores();
    }

}
