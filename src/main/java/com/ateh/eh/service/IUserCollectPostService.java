package com.ateh.eh.service;

import com.ateh.eh.entity.UserCollectPost;
import com.ateh.eh.entity.UserHistoryPost;
import com.ateh.eh.req.history.UserHistoryPostPageReq;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: IUserHistoryPostService.java
 *
 * @author huang.yijie
 * 时间: 2023/5/7 14:52
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
public interface IUserCollectPostService extends IService<UserCollectPost> {

    /**
     * 功能描述: 新增收藏
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/8 23:32
     */
    Result addCollect(UserCollectPost collectPost);

    /**
     * 功能描述: 查询是否已经成功收藏
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/9 0:13
     */
    Result qryIsCollect(UserCollectPost collectPost);

    /**
     * 功能描述: 查询收藏的帖子
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/9 14:07
     */
    Result qryCollectPostPage(UserHistoryPostPageReq req);
}
