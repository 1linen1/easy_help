package com.ateh.eh.service;

import com.ateh.eh.entity.Message;
import com.ateh.eh.entity.User;
import com.ateh.eh.req.message.MessagePageReq;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: IMessageService.java
 *
 * @author huang.yijie
 * 时间: 2023/5/9 23:02
 */
public interface IMessageService extends IService<Message> {

    /**
     * 功能描述: 分页查询消息
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/9 23:05
     */
    Result qryMessagePage(MessagePageReq req);
}
