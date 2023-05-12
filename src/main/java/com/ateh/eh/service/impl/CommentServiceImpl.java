package com.ateh.eh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.Comment;
import com.ateh.eh.entity.UserHelpPost;
import com.ateh.eh.entity.ext.CommentExt;
import com.ateh.eh.mapper.CommentMapper;
import com.ateh.eh.mapper.UserHelpPostMapper;
import com.ateh.eh.req.comment.AddCommentReq;
import com.ateh.eh.req.comment.CommentPageReq;
import com.ateh.eh.req.comment.DeleteCommentReq;
import com.ateh.eh.service.ICommentService;
import com.ateh.eh.utils.Result;
import com.ateh.eh.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    private UserHelpPostMapper helpPostMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result qryPostCommentPage(CommentPageReq req) {
        Page<CommentExt> commentPage = commentMapper.qryPostCommentPage(req.toPage(), req);
        return Result.success(commentPage);
    }

    @Override
    public Result addComment(AddCommentReq req) {
        Comment comment = BeanUtil.toBean(req, Comment.class);
        commentMapper.insert(comment);

        Long userId = UserHolder.getLoginUser().getUserId();
        if (userId.equals(req.getPostUserId())) {
            return Result.success("评论成功!");
        }

        String value = redisTemplate.opsForValue().get(RedisConstants.POST_HELP + req.getUserId() + ":" + req.getPostId());
        if (StrUtil.isEmpty(value)) {
            UserHelpPost userHelpPost = new UserHelpPost();
            userHelpPost.setPostId(req.getPostId());
            userHelpPost.setPositiveUserId(req.getUserId());
            userHelpPost.setPassiveUserId(req.getPostUserId());
            userHelpPost.setScores(0L);
            helpPostMapper.insert(userHelpPost);
            redisTemplate.opsForValue().set(RedisConstants.POST_HELP + req.getUserId() + ":" + req.getPostId(), "1");
        } else {
            long num = Long.parseLong(value);
            redisTemplate.opsForValue().set(RedisConstants.POST_HELP + req.getUserId() + ":" + req.getPostId(), String.valueOf(++num));
        }

        return Result.success("评论成功！");
    }

    @Override
    public Result deleteComment(DeleteCommentReq req) {
        Comment comment = new Comment();
        comment.setCommentId(req.getCommentId());
        comment.setStatus(CommonConstants.STATUS_INVALID);
        commentMapper.updateById(comment);

        Long userId = UserHolder.getLoginUser().getUserId();
        if (userId.equals(req.getPostUserId())) {
            return Result.success("删除成功!");
        }

        String value = redisTemplate.opsForValue().get(RedisConstants.POST_HELP + req.getUserId() + ":" + req.getPostId());

        if (StrUtil.isNotBlank(value)) {
            long num = Long.parseLong(value);
            if (num <= 1) {
                // 说明这个用户在该帖子下没有评论了
                // 二次校验
                LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
                lqw.eq(Comment::getPostId, req.getPostId());
                lqw.eq(Comment::getUserId, req.getUserId());
                lqw.eq(Comment::getStatus, CommonConstants.STATUS_VALID);
                long count = commentMapper.selectCount(lqw);
                if (count <= 1) {
                    UserHelpPost userHelpPost = new UserHelpPost();
                    userHelpPost.setStatus(CommonConstants.STATUS_INVALID);

                    LambdaQueryWrapper<UserHelpPost> lqw2 = new LambdaQueryWrapper<>();
                    lqw2.eq(UserHelpPost::getPostId, req.getPostId());
                    lqw2.eq(UserHelpPost::getPassiveUserId, req.getPostUserId());
                    lqw2.eq(UserHelpPost::getPositiveUserId, req.getUserId());
                    lqw2.ne(UserHelpPost::getStatus, CommonConstants.STATUS_INVALID);
                    helpPostMapper.update(userHelpPost, lqw2);

                    redisTemplate.delete(RedisConstants.POST_HELP + req.getUserId() + ":" + req.getPostId());
                } else {
                    redisTemplate.opsForValue().set(RedisConstants.POST_HELP + req.getUserId() + ":" + req.getPostId(), String.valueOf(--count));
                }
            } else {
                redisTemplate.opsForValue().set(RedisConstants.POST_HELP + req.getUserId() + ":" + req.getPostId(), String.valueOf(--num));
            }
        }

        return Result.success("删除成功!");
    }
}
