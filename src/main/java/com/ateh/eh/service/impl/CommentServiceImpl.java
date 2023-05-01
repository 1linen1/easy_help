package com.ateh.eh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.entity.Comment;
import com.ateh.eh.entity.ext.CommentExt;
import com.ateh.eh.mapper.CommentMapper;
import com.ateh.eh.req.comment.AddCommentReq;
import com.ateh.eh.req.comment.CommentPageReq;
import com.ateh.eh.service.ICommentService;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: CommentServiceImpl.java
 *
 * @author huang.yijie
 * 时间: 2023/4/24 15:06
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Result qryPostCommentPage(CommentPageReq req) {
        Page<CommentExt> commentPage = commentMapper.qryPostCommentPage(req.toPage(), req);
        return Result.success(commentPage);
    }

    @Override
    public Result addComment(AddCommentReq req) {
        Comment comment = BeanUtil.toBean(req, Comment.class);
        commentMapper.insert(comment);
        return Result.success("评论成功！");
    }

    @Override
    public Result deleteComment(Long commentId) {
        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setStatus(CommonConstants.STATUS_INVALID);
        commentMapper.updateById(comment);
        return Result.success("删除成功");
    }
}
