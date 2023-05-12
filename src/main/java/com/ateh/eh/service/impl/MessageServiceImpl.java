package com.ateh.eh.service.impl;

import com.ateh.eh.entity.Message;
import com.ateh.eh.mapper.MessageMapper;
import com.ateh.eh.req.message.MessagePageReq;
import com.ateh.eh.service.IMessageService;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright: Copyright(c) 2022 iwhalecloud
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: MessageServiceImpl.java
 *
 * @author huang.yijie
 * 时间: 2023/5/9 23:04
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Result qryMessagePage(MessagePageReq req) {
        return Result.success(messageMapper.qryMessagePage(req.toPage(), req));
    }
}
