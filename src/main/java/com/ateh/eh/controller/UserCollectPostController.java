package com.ateh.eh.controller;

import com.ateh.eh.entity.UserCollectPost;
import com.ateh.eh.entity.UserHistoryPost;
import com.ateh.eh.req.history.UserHistoryPostPageReq;
import com.ateh.eh.service.IUserCollectPostService;
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
@RequestMapping("/api/collect")
@Api("收藏记录接口")
public class UserCollectPostController {

    @Autowired
    private IUserCollectPostService collectPostService;

    @PostMapping("/addCollect")
    @ApiOperation("新增/取消 收藏")
    public Result addCollect(@RequestBody UserCollectPost collectPost) {
        return collectPostService.addCollect(collectPost);
    }

    @PostMapping("/qryIsCollect")
    @ApiOperation("查询是否成功收藏")
    public Result qryIsCollect(@RequestBody UserCollectPost collectPost) {
        return collectPostService.qryIsCollect(collectPost);
    }

    @PostMapping("/qryCollectPostPage")
    @ApiOperation("分页查询收藏帖子")
    public Result qryCollectPostPage(@RequestBody UserHistoryPostPageReq req) {
        return collectPostService.qryCollectPostPage(req);
    }

}
