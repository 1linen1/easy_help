package com.ateh.eh.controller;

import com.ateh.eh.entity.Follows;
import com.ateh.eh.req.comment.AddCommentReq;
import com.ateh.eh.service.IFollowsService;
import com.ateh.eh.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: FollowsController.java
 *
 * @author huang.yijie
 * 时间: 2023/5/1 23:08
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@RestController
@RequestMapping("/api/follows")
@Api("粉丝接口")
public class FollowsController {

    @Autowired
    private IFollowsService followsService;

    @PostMapping("/addFollow")
    @ApiOperation("新增粉丝")
    public Result addFollow(@RequestBody Follows follows) {
        return followsService.addFollow(follows);
    }

    @PostMapping("/removeFollow")
    @ApiOperation("减少粉丝")
    public Result removeFollow(@RequestBody Follows follows) {
        return followsService.removeFollow(follows);
    }

    @GetMapping("/isFollow/{userId}")
    @ApiOperation("是否关注该用户")
    public Result isFollow(@PathVariable("userId")Long userId) {
        return followsService.isFollow(userId);
    }

}
