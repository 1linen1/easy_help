package com.ateh.eh.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.Recommend;
import com.ateh.eh.entity.UserCollectPost;
import com.ateh.eh.entity.UserHistoryPost;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.mapper.RecommendMapper;
import com.ateh.eh.mapper.UserCollectPostMapper;
import com.ateh.eh.mapper.UserHistoryPostMapper;
import com.ateh.eh.req.history.UserHistoryPostPageReq;
import com.ateh.eh.service.IUserCollectPostService;
import com.ateh.eh.service.IUserHistoryPostService;
import com.ateh.eh.utils.Result;
import com.ateh.eh.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * 类名称: UserHistoryPostServiceImpl.java
 *
 * @author huang.yijie
 * 时间: 2023/5/7 14:53
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Service
public class UserCollectPostServiceImpl extends ServiceImpl<UserCollectPostMapper, UserCollectPost> implements IUserCollectPostService {

    @Autowired
    private UserCollectPostMapper collectPostMapper;

    @Autowired
    private RecommendMapper recommendMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result addCollect(UserCollectPost collectPost) {
        Long postId = collectPost.getPostId();
        String value = redisTemplate.opsForValue().get(RedisConstants.POST_COLLECT + collectPost.getUserId() + ":" + postId);
        if (StrUtil.isEmpty(value)) {
            collectPostMapper.insert(collectPost);
            redisTemplate.opsForValue().set(RedisConstants.POST_COLLECT + collectPost.getUserId() + ":" + postId,
                    String.valueOf(collectPost.getCollectId()));
        } else {
            collectPost.setCollectId(Long.valueOf(value));
            collectPost.setStatus(CommonConstants.STATUS_INVALID);
            collectPostMapper.updateById(collectPost);
            redisTemplate.delete(RedisConstants.POST_COLLECT + collectPost.getUserId() + ":" + postId);
        }

        Recommend recommend = new Recommend();
        Long userId = UserHolder.getLoginUser().getUserId();
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
                if (StrUtil.isEmpty(value)) {
                    recommend.setLoves(2);
                } else {
                    recommend.setLoves(0);
                }
                recommendMapper.insert(recommend);
            } else {
                // 说明只是Redis中没有数据，更新数据库数据，并存入Redis中
                float loveNum = recommend.getLoves();
                if (StrUtil.isEmpty(value)) {
                    recommend.setLoves(loveNum + 2);
                } else {
                    recommend.setLoves(loveNum - 2);
                }
                recommend.setRecommendId(result.getRecommendId());
                recommendMapper.updateById(recommend);
            }
            redisTemplate.opsForHash().put(RedisConstants.RECOMMEND + userId + ":" + postId, "recommend_id", String.valueOf(recommend.getRecommendId()));
        } else  {
            // 说明缓存中有值，直接使用缓存中保存的recommendId和loves，更新数据库与缓存就行
            Long id = Long.valueOf((String) recommendId);
            float loveNum = Float.parseFloat((String) loves);
            recommend.setRecommendId(id);
            if (StrUtil.isEmpty(value)) {
                recommend.setLoves(loveNum + 2);
            } else {
                recommend.setLoves(loveNum - 2);
            }
            recommendMapper.updateById(recommend);
        }
        redisTemplate.opsForHash().put(RedisConstants.RECOMMEND + userId + ":" + postId, "loves", String.valueOf(recommend.getLoves()));


        return Result.success("");
    }

    @Override
    public Result qryIsCollect(UserCollectPost collectPost) {
        String value = redisTemplate.opsForValue().get(RedisConstants.POST_COLLECT + collectPost.getUserId() + ":" + collectPost.getPostId());

        return Result.success(StrUtil.isNotEmpty(value));
    }

    @Override
    public Result qryCollectPostPage(UserHistoryPostPageReq req) {
        IPage<PostExt> postPage = collectPostMapper.qryCollectPostPage(req.toPage(), req);
        List<PostExt> records = postPage.getRecords();
        records.forEach(record -> record.setIsCollect(
                StrUtil.isNotEmpty(redisTemplate.opsForValue().get(RedisConstants.POST_COLLECT + req.getUserId() + ":" + record.getPostId()))
        ));
        postPage.setRecords(records);
        return Result.success(postPage);
    }

}
