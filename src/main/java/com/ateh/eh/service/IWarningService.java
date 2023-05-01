package com.ateh.eh.service;

import com.ateh.eh.entity.Warning;
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
    Result handleWarning(Warning warning);
}
