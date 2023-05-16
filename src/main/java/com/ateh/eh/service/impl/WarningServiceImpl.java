package com.ateh.eh.service.impl;

import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.entity.Comment;
import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.User;
import com.ateh.eh.entity.Warning;
import com.ateh.eh.mapper.CommentMapper;
import com.ateh.eh.mapper.PostMapper;
import com.ateh.eh.mapper.UserMapper;
import com.ateh.eh.mapper.WarningMapper;
import com.ateh.eh.req.warning.HandleAppealReq;
import com.ateh.eh.req.warning.HandleWarningReq;
import com.ateh.eh.req.warning.WarningPageReq;
import com.ateh.eh.service.IWarningService;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: WarningServiceImpl.java
 *
 * @author huang.yijie
 * 时间: 2023/5/1 21:11
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Service
public class WarningServiceImpl extends ServiceImpl<WarningMapper, Warning> implements IWarningService {
    @Autowired
    private WarningMapper warningMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Result addWarning(Warning warning) {
        LambdaQueryWrapper<Warning> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Warning::getCommentPostId, warning.getCommentPostId());
        lqw.eq(Warning::getPositiveUserId, warning.getPositiveUserId());
        lqw.eq(Warning::getPassiveUserId, warning.getPassiveUserId());
        lqw.eq(Warning::getType, warning.getType());
        Long count = warningMapper.selectCount(lqw);
        if (count > 0) {
            return Result.error("您已举报过该内容!");
        }
        warning.setScores(0L);
        warningMapper.insert(warning);
        return Result.success("举报成功!");
    }

    @Override
    public Result handleWarning(HandleWarningReq req) {
        Warning warning = new Warning();
        warning.setWarningId(req.getWarningId());
        StringBuilder result = new StringBuilder();
        if (Boolean.FALSE.equals(req.getIsDeletePost()) && Boolean.FALSE.equals(req.getIsDeleteUser()) && Boolean.FALSE.equals(req.getIsDeleteComment())) {
            // 如果都是False，说明不作处理，更新举报状态即可
            warning.setStatus(CommonConstants.WARNING_FAIL);
            warning.setResult("");
            warningMapper.updateById(warning);
            return Result.success("处理成功!");
        }
        if (Boolean.TRUE.equals(req.getIsDeletePost()) && !Objects.isNull(req.getPostId())) {
            Post post = new Post();
            post.setPostId(req.getPostId());
            post.setStatus(CommonConstants.STATUS_INVALID);
            result.append("1");
            postMapper.updateById(post);
        }
        if (Boolean.TRUE.equals(req.getIsDeleteUser()) && !Objects.isNull(req.getUserId())) {
            User user = new User();
            user.setUserId(req.getUserId());
            user.setStatus(CommonConstants.STATUS_INVALID);
            result.append("2");
            userMapper.updateById(user);
        }
        if (Boolean.TRUE.equals(req.getIsDeleteComment()) && !Objects.isNull(req.getCommentId())) {
            Comment comment = new Comment();
            comment.setCommentId(req.getCommentId());
            comment.setStatus(CommonConstants.STATUS_INVALID);
            result.append("0");
            commentMapper.updateById(comment);
        }
        warning.setResult(String.valueOf(result));
        warning.setStatus(CommonConstants.WARNING_SUCCESS);
        warningMapper.updateById(warning);
        return Result.success("处理成功!");
    }

    @Override
    public Result qryAllWarning(WarningPageReq req) {
        return Result.success(warningMapper.qryAllWarning(req.toPage(), req));
    }

    @Override
    public Result updateWarning(Warning warning) {
        warningMapper.updateById(warning);
        return Result.success("删除成功!");
    }

    @Override
    public Result handleAppeal(HandleAppealReq req) {
        Warning warning = new Warning();
        warning.setWarningId(req.getWarningId());

        if (Boolean.TRUE.equals(req.getIsNeedAssign())) {
            Post post = postMapper.selectById(req.getPostId());
            // 减去被分配的积分
            post.setScores(post.getScores() - req.getScores());
            postMapper.updateById(post);

            User user = userMapper.selectById(req.getPositiveUserId());
            user.setScoresCurrent(user.getScoresCurrent() + req.getScores());
            user.setScoresTotal(user.getScoresTotal() + req.getScores());
            userMapper.updateById(user);

            warning.setScores(req.getScores());
            warning.setStatus(CommonConstants.APPEAL_SUCCESS);
        } else {
            warning.setStatus(CommonConstants.APPEAL_FAIL);
        }
        warningMapper.updateById(warning);
        return Result.success("");
    }
}
