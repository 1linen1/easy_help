package com.ateh.eh.service;

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
public interface IUserHistoryPostService extends IService<UserHistoryPost> {
    /**
     * 功能描述: 新增浏览历史
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/7 15:43
     */
    Result addHistory(UserHistoryPost historyPost);

    /**
     * 功能描述: 分页查询浏览历史
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/7 15:49
     */
    Result qryHistoryPage(UserHistoryPostPageReq req);
}
