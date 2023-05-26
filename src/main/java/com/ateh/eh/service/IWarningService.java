package com.ateh.eh.service;

import com.ateh.eh.entity.Warning;
import com.ateh.eh.req.warning.HandleAppealReq;
import com.ateh.eh.req.warning.HandleWarningReq;
import com.ateh.eh.req.warning.WarningPageReq;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: IWarningService.java
 *
 * @author huang.yijie
 * 时间: 2023/5/1 21:09
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
public interface IWarningService extends IService<Warning> {

    /**
     * 功能描述: 新增举报信息
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/1 21:15
     */
    Result addWarning(Warning warning);

    /**
     * 功能描述: 处理结果
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/1 21:23
     */
    Result handleWarning(HandleWarningReq req);

    /**
     * 功能描述: 分页查询全部举报信息
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/15 23:22
     */
    Result qryAllWarning(WarningPageReq req);

    /**
     * 功能描述: 更新举报信息
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/16 18:20
     */
    Result updateWarning(Warning warning);

    /**
     * 功能描述: 处理申诉帖子
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/16 21:53
     */
    Result handleAppeal(HandleAppealReq req);

    /**
     * 功能描述: TODO
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/17 15:40
     */
    Result qryMyWarning(WarningPageReq req);
}
