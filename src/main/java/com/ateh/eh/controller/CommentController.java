package com.ateh.eh.controller;

import com.ateh.eh.req.comment.AddCommentReq;
import com.ateh.eh.req.comment.CommentPageReq;
import com.ateh.eh.service.ICommentService;
import com.ateh.eh.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
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
 * 类名称: CommentController.java
 *
 * @author huang.yijie
 * 时间: 2023/4/24 15:04
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@RestController
@RequestMapping("/api/comment")
@Api("评论接口")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @PostMapping("/addComment")
    @ApiOperation("新增评论")
    public Result addComment(@RequestBody AddCommentReq req) {
        return commentService.addComment(req);
    }

    @GetMapping("/deleteComment/{commentId}")
    @ApiOperation("删除评论")
    public Result deleteComment(@PathVariable("commentId") Long commentId) {
        return commentService.deleteComment(commentId);
    }

    @PostMapping("/qryPostCommentPage")
    @ApiOperation("分页查询评论")
    public Result qryPostCommentPage(@RequestBody CommentPageReq req) {
        return commentService.qryPostCommentPage(req);
    }

}
