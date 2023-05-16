package com.ateh.eh.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.Recommend;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.entity.ext.UserExt;
import com.ateh.eh.mapper.FollowsMapper;
import com.ateh.eh.mapper.PostMapper;
import com.ateh.eh.mapper.RecommendMapper;
import com.ateh.eh.mapper.UserMapper;
import com.ateh.eh.req.follows.FollowsPageReq;
import com.ateh.eh.req.posts.PostPageReq;
import com.ateh.eh.req.user.MyRankReq;
import com.ateh.eh.service.IPostService;
import com.ateh.eh.service.IUserService;
import com.ateh.eh.utils.EmailTask;
import com.ateh.eh.utils.Result;
import com.ateh.eh.utils.ScoresUtil;
import com.ateh.eh.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.Scope;
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
@Scope(name = "prototype", description = "")
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService{

    @Autowired
    private EmailTask emailTask;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private FollowsMapper followsMapper;

    @Autowired
    private RecommendMapper recommendMapper;

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
    public Result addPostViews(Post post) {
        long finalViews;
        Long postId = post.getPostId();
        String views = redisTemplate.opsForValue().get(RedisConstants.POST_VIEWS + postId);
        Post nPost = new Post();
        if (StrUtil.isBlank(views)) {
            nPost = postMapper.selectById(postId);
            finalViews = nPost.getViews();
        } else {
            finalViews = Long.parseLong(views);
        }
        ++finalViews;
        redisTemplate.opsForValue().set(RedisConstants.POST_VIEWS + postId, String.valueOf(finalViews));

        nPost.setPostId(postId);
        nPost.setViews(finalViews);
        postMapper.updateById(nPost);

        Long userId = UserHolder.getLoginUser().getUserId();

        // 协同推荐
        if (!Objects.equals(post.getUserId(), userId)) {
            MyRankReq rankReq = new MyRankReq();
            rankReq.setOrderType("Total");
            rankReq.setUserId(post.getUserId());
            Result<UserExt> myRank = userService.getMyRank(rankReq);
            UserExt data = myRank.getData();
            float extNum = (float) (0.5 / data.getRank());

            recommendOperation(postId, userId, 0.5F + extNum);
        }

        return Result.success("");
    }

    private void recommendOperation(Long postId, Long userId, float num) {
        Recommend recommend = new Recommend();
        Object loves = redisTemplate.opsForHash().get(RedisConstants.RECOMMEND + userId + ":" + postId, "loves");
        Object recommendId = redisTemplate.opsForHash().get(RedisConstants.RECOMMEND + userId + ":" + postId, "recommend_id");

        if (Objects.isNull(loves) || Objects.isNull(recommendId)) {
            LambdaQueryWrapper<Recommend> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Recommend::getUserId, userId);
            lqw.eq(Recommend::getPostId, postId);
            lqw.eq(Recommend::getStatus, CommonConstants.STATUS_VALID);
            Recommend result = recommendMapper.selectOne(lqw);
            if (Objects.isNull(result)) {
                // 说明数据库和缓存中都没有值，插入数据
                recommend.setPostId(postId);
                recommend.setUserId(userId);
                recommend.setLoves(0.5F);
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
            Long id = Long.valueOf((String) recommendId);
            recommend.setRecommendId(id);
            recommend.setLoves(Float.parseFloat((String) loves) + num);
            recommendMapper.updateById(recommend);
        }
        redisTemplate.opsForHash().put(RedisConstants.RECOMMEND + userId + ":" + postId, "loves", String.valueOf(recommend.getLoves()));
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

    @Override
    public Result qryRecommendPost(PostPageReq req) {
        emailTask.calRecommendSync(req.getUserId());
        String str = redisTemplate.opsForValue().get(RedisConstants.RECOMMEND_POST + req.getUserId());
        List<PostExt> postExts;
        List<PostExt> collects;
        if (StrUtil.isNotEmpty(str)) {
            postExts = JSON.parseArray(str, PostExt.class);
            // 过滤出没有被用户看过的帖子
            assert postExts != null;
            collects = postExts.stream()
                    .filter(item -> Boolean.FALSE.equals(redisTemplate.opsForSet().isMember(RedisConstants.RECOMMENDED_POST + req.getUserId(),
                            String.valueOf(item.getPostId()))))
                    .limit(10)
                    .collect(Collectors.toList());
            if (collects.size() <= 0) {
                postExts = postMapper.randomSelectPost(100);
                collects = postExts.stream()
                        .filter(item -> Boolean.FALSE.equals(redisTemplate.opsForSet().isMember(RedisConstants.RECOMMENDED_POST + req.getUserId(),
                                String.valueOf(item.getPostId()))))
                        .limit(10)
                        .collect(Collectors.toList());
            }
            collects.forEach(item -> redisTemplate.opsForSet().add(RedisConstants.RECOMMENDED_POST + req.getUserId(),
                    String.valueOf(item.getPostId())));

        } else {
            postExts = postMapper.randomSelectPost(100);
            collects = postExts.stream()
                    .filter(item -> Boolean.FALSE.equals(redisTemplate.opsForSet().isMember(RedisConstants.RECOMMENDED_POST + req.getUserId(),
                            String.valueOf(item.getPostId()))))
                    .limit(10)
                    .collect(Collectors.toList());
            collects.forEach(item -> redisTemplate.opsForSet().add(RedisConstants.RECOMMENDED_POST + req.getUserId(),
                    String.valueOf(item.getPostId())));
        }

        return Result.success(collects);
    }

    @Override
    public Result updatePost(Post post) {
        postMapper.updateById(post);
        if (CommonConstants.STATUS_VALID.equals(post.getStatus())) {
            return Result.success("恢复成功!");
        } else {
            return Result.success("删除成功!");
        }
    }

    @Override
    public Result getAllPost(PostPageReq req) {
        return Result.success(postMapper.getAllPost(req.toPage(), req));
    }
}
