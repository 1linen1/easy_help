package com.ateh.eh.controller;

import com.ateh.eh.entity.UserHistoryPost;
import com.ateh.eh.req.history.UserHistoryPostPageReq;
import com.ateh.eh.req.user.UserLoginReq;
import com.ateh.eh.service.IUserHistoryPostService;
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
 * 类名称: UserHistoryPostController.java
 *
 * @author huang.yijie
 * 时间: 2023/5/7 14:49
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@RestController
@RequestMapping("/api/history")
@Api("浏览历史接口")
public class UserHistoryPostController {

    @Autowired
    private IUserHistoryPostService historyPostService;

    @PostMapping("/addHistory")
    @ApiOperation("新增浏览历史")
    public Result addHistory(@RequestBody UserHistoryPost historyPost) {
        return historyPostService.addHistory(historyPost);
    }

    @PostMapping("/qryHistoryPage")
    @ApiOperation("分页查询浏览历史")
    public Result qryHistoryPage(@RequestBody UserHistoryPostPageReq req) {
        return historyPostService.qryHistoryPage(req);
    }

}
