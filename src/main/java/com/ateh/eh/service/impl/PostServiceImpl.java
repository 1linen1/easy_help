package com.ateh.eh.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.mapper.PostMapper;
import com.ateh.eh.req.posts.PostPageReq;
import com.ateh.eh.service.IPostService;
import com.ateh.eh.utils.Result;
import com.ateh.eh.utils.UserHolder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    private StringRedisTemplate redisTemplate;

    @Override
    public Result addPost(Post post) {
        post.setUserId(UserHolder.getLoginUser().getUserId());
        post.setViews(0L);
        post.setComments(0L);
        post.setLoves(0L);
        post.setScores(0L);
        post.setCollects(0L);
        postMapper.insert(post);
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

}
