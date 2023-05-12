package com.ateh.eh.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.User;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.entity.ext.UserExt;
import com.ateh.eh.mapper.FollowsMapper;
import com.ateh.eh.mapper.PostMapper;
import com.ateh.eh.mapper.UserMapper;
import com.ateh.eh.req.follows.FollowsPageReq;
import com.ateh.eh.req.posts.PostPageReq;
import com.ateh.eh.service.IPostService;
import com.ateh.eh.utils.Result;
import com.ateh.eh.utils.ScoresUtil;
import com.ateh.eh.utils.UserHolder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: PostServiceImpl.java
 *
 * @author huang.yijie
 * 时间: 2023/4/8 19:51
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService{

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FollowsMapper followsMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result addPost(Post post) {
        Long userId = UserHolder.getLoginUser().getUserId();
        post.setUserId(userId);
        post.setViews(0L);
        post.setComments(0L);
        post.setLoves(0L);
        post.setCollects(0L);
        post.setResolved(CommonConstants.POST_UNRESOLVED);

        postMapper.insert(post);

        userMapper.updateScoresCurrent(post.getScores(), userId);

        return Result.success("发帖成功!");
    }

    @Override
    public Result<IPage<PostExt>> qryPostPage(PostPageReq req) {
        IPage<PostExt> posts = postMapper.qryPostPage(req.toPage(), req);
        return Result.success(posts);
    }

    @Override
    public Result addPostViews(Long postId) {
        long finalViews;
        String views = redisTemplate.opsForValue().get(RedisConstants.POST_VIEWS + postId);
        Post post = new Post();
        if (StrUtil.isBlank(views)) {
            post = postMapper.selectById(postId);
            finalViews = post.getViews();
        } else {
            finalViews = Long.parseLong(views);
        }
        ++finalViews;
        redisTemplate.opsForValue().set(RedisConstants.POST_VIEWS + postId, String.valueOf(finalViews));

        post.setPostId(postId);
        post.setViews(finalViews);
        postMapper.updateById(post);
        return Result.success("");
    }

    @Override
    public Long qryValidScores(Long postId) {
        Post post = postMapper.selectById(postId);
        return ScoresUtil.getPostValidPost(post);
    }

    @Override
    public Result qryHelpPostPage(PostPageReq req) {

        return Result.success(postMapper.qryHelpPostPage(req.toPage(), req));
    }

    @Override
    public Result<IPage<PostExt>> qryPostPageByContent(PostPageReq req) {
        IPage<PostExt> posts = postMapper.qryPostPageByContent(req.toPage(), req);
        return Result.success(posts);
    }

    @Override
    public Result<IPage<PostExt>> qryDynamicPage(PostPageReq req) {
        FollowsPageReq followReq = new FollowsPageReq();
        followReq.setPageNum(1);
        followReq.setPageSize(1000);
        followReq.setUserId(req.getUserId());
        followReq.setType("concerns"); // 查询关注
        IPage<UserExt> userPage = followsMapper.qryConcernsOrFollowsPage(followReq.toPage(), followReq);

        List<UserExt> records = userPage.getRecords();
        List<Long> ids = records.stream().map(UserExt::getUserId).collect(Collectors.toList());

        ids.add(req.getUserId());

        IPage<PostExt> posts = postMapper.qryDynamicPage(req.toPage(), req, ids);
        return Result.success(posts);
    }
}
