package com.ateh.eh.service;

import com.ateh.eh.entity.Comment;
import com.ateh.eh.req.comment.AddCommentReq;
import com.ateh.eh.req.comment.CommentPageReq;
import com.ateh.eh.req.comment.DeleteCommentReq;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: ICommentService.java
 *
 * @author huang.yijie
 * 时间: 2023/4/24 15:05
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 功能描述: 分页获取帖子评论
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/4/24 15:20
     */
    Result qryPostCommentPage(CommentPageReq req);

    /**
     * 功能描述: 新增评论
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/4/24 15:29
     */
    Result addComment(AddCommentReq req);

    /**
     * 功能描述: 删除评论
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/4/24 15:34
     */
    Result deleteComment(DeleteCommentReq req);

    /**
     * 功能描述: 获取评论信息
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/16 19:06
     */
    Result getCommentById(Long commentId);
}
