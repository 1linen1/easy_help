package com.ateh.eh.controller;

import com.ateh.eh.mapper.MessageMapper;
import com.ateh.eh.req.message.MessagePageReq;
import com.ateh.eh.service.IMessageService;
import com.ateh.eh.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: MessageController.java
 *
 * @author huang.yijie
 * 时间: 2023/5/2 21:22
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@RestController
@RequestMapping("/api/message")
@Api("消息接口")
public class MessageController {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private IMessageService messageService;

    @PostMapping("/qryMessagePage")
    @ApiOperation("分页查询消息")
    public Result qryMessagePage(@RequestBody MessagePageReq req) {
        return messageService.qryMessagePage(req);
    }

}
