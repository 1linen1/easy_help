package com.ateh.eh.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.UserCollectPost;
import com.ateh.eh.entity.UserHistoryPost;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.mapper.UserCollectPostMapper;
import com.ateh.eh.mapper.UserHistoryPostMapper;
import com.ateh.eh.req.history.UserHistoryPostPageReq;
import com.ateh.eh.service.IUserCollectPostService;
import com.ateh.eh.service.IUserHistoryPostService;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private StringRedisTemplate redisTemplate;

    @Override
    public Result addCollect(UserCollectPost collectPost) {
        String value = redisTemplate.opsForValue().get(RedisConstants.POST_COLLECT + collectPost.getUserId() + ":" + collectPost.getPostId());
        if (StrUtil.isEmpty(value)) {
            collectPostMapper.insert(collectPost);
            redisTemplate.opsForValue().set(RedisConstants.POST_COLLECT + collectPost.getUserId() + ":" + collectPost.getPostId(),
                    String.valueOf(collectPost.getCollectId()));
        } else {
            collectPost.setCollectId(Long.valueOf(value));
            collectPost.setStatus(CommonConstants.STATUS_INVALID);
            collectPostMapper.updateById(collectPost);
            redisTemplate.delete(RedisConstants.POST_COLLECT + collectPost.getUserId() + ":" + collectPost.getPostId());
        }
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
