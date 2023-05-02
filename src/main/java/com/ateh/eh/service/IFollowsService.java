package com.ateh.eh.service;

import com.ateh.eh.entity.Follows;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: IFollowsService.java
 *
 * @author huang.yijie
 * 时间: 2023/5/1 23:10
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
public interface IFollowsService extends IService<Follows> {
    /**
     * 功能描述: 新增粉丝
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/1 23:15
     */
    Result addFollow(Follows follows);

    /**
     * 功能描述: 减少粉丝
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/1 23:16
     */
    Result removeFollow(Follows follows);

    /**
     * 功能描述: 查询是否关注该用户
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/1 23:33
     */
    Result isFollow(Long userId);
}
