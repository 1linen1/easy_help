package com.ateh.eh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.Comment;
import com.ateh.eh.entity.Recommend;
import com.ateh.eh.entity.User;
import com.ateh.eh.entity.UserHelpPost;
import com.ateh.eh.entity.ext.CommentExt;
import com.ateh.eh.entity.ext.UserExt;
import com.ateh.eh.mapper.CommentMapper;
import com.ateh.eh.mapper.RecommendMapper;
import com.ateh.eh.mapper.UserHelpPostMapper;
import com.ateh.eh.mapper.UserMapper;
import com.ateh.eh.req.comment.AddCommentReq;
import com.ateh.eh.req.comment.CommentPageReq;
import com.ateh.eh.req.comment.DeleteCommentReq;
import com.ateh.eh.req.user.MyRankReq;
import com.ateh.eh.service.ICommentService;
import com.ateh.eh.service.IUserService;
import com.ateh.eh.utils.Result;
import com.ateh.eh.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    private RecommendMapper recommendMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService userService;

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

        Long postId = req.getPostId();
        String value = redisTemplate.opsForValue().get(RedisConstants.POST_HELP + req.getUserId() + ":" + postId);
        if (StrUtil.isEmpty(value)) {
            UserHelpPost userHelpPost = new UserHelpPost();
            userHelpPost.setPostId(postId);
            userHelpPost.setPositiveUserId(req.getUserId());
            userHelpPost.setPassiveUserId(req.getPostUserId());
            userHelpPost.setScores(0L);
            helpPostMapper.insert(userHelpPost);
            redisTemplate.opsForValue().set(RedisConstants.POST_HELP + req.getUserId() + ":" + postId, "1");
        } else {
            long num = Long.parseLong(value);
            redisTemplate.opsForValue().set(RedisConstants.POST_HELP + req.getUserId() + ":" + postId, String.valueOf(++num));
        }

        MyRankReq rankReq = new MyRankReq();
        rankReq.setOrderType("Total");
        rankReq.setUserId(req.getPostUserId());
        Result<UserExt> myRank = userService.getMyRank(rankReq);
        UserExt data = myRank.getData();
        float extNum = (float) (1.0 / data.getRank());

        // 协同推荐使用
        if (!Objects.equals(req.getPostUserId(), userId)) {
            recommendOperation(userId, postId, 1 + extNum);
        }

        return Result.success("评论成功！");
    }

    /**
     * 更新协同推荐数据
     *
     * @param userId
     * @param postId
     */
    private void recommendOperation(Long userId, Long postId, float num) {
        Recommend recommend = new Recommend();
        Object loves = redisTemplate.opsForHash().get(RedisConstants.RECOMMEND + userId + ":" + postId, "loves");
        Object recommendId = redisTemplate.opsForHash().get(RedisConstants.RECOMMEND + userId + ":" + postId, "recommend_id");

        if (Objects.isNull(loves)) {
            LambdaQueryWrapper<Recommend> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Recommend::getUserId, userId);
            lqw.eq(Recommend::getPostId, postId);
            lqw.eq(Recommend::getStatus, CommonConstants.STATUS_VALID);
            Recommend result = recommendMapper.selectOne(lqw);
            if (Objects.isNull(result)) {
                // 说明数据库和缓存中都没有值，插入数据
                recommend.setPostId(postId);
                recommend.setUserId(userId);
                if (num < 0) {
                    recommend.setLoves(0);
                } else {
                    recommend.setLoves(num);
                }
                recommendMapper.insert(recommend);
            } else {
                // 说明只是Redis中没有数据，更新数据库数据，并存入Redis中
                float loveNum = recommend.getLoves();
                recommend.setLoves(loveNum + num);
                recommend.setRecommendId(result.getRecommendId());
                recommendMapper.updateById(recommend);
            }
            redisTemplate.opsForHash().put(RedisConstants.RECOMMEND + userId + ":" + postId, "recommend_id", String.valueOf(recommend.getRecommendId()));
        } else  {
            // 说明缓存中有值，直接使用缓存中保存的recommendId和loves，更新数据库与缓存就行
            assert recommendId != null;
            Long id = Long.valueOf((String) recommendId);
            recommend.setRecommendId(id);
            recommend.setLoves(Float.parseFloat((String) loves) + num);
            recommendMapper.updateById(recommend);
        }
        redisTemplate.opsForHash().put(RedisConstants.RECOMMEND + userId + ":" + postId, "loves", String.valueOf(recommend.getLoves()));
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

        // 协同推荐使用
        recommendOperation(userId, req.getPostId(), -1);

        return Result.success("删除成功!");
    }

    @Override
    public Result getCommentById(Long commentId) {
        return Result.success(commentMapper.selectById(commentId));
    }
}
