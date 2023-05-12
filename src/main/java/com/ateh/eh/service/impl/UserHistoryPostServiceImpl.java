package com.ateh.eh.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.UserHistoryPost;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.mapper.UserHistoryPostMapper;
import com.ateh.eh.req.history.UserHistoryPostPageReq;
import com.ateh.eh.service.IUserHistoryPostService;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

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
public class UserHistoryPostServiceImpl extends ServiceImpl<UserHistoryPostMapper, UserHistoryPost> implements IUserHistoryPostService {

    @Autowired
    private UserHistoryPostMapper historyPostMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result addHistory(UserHistoryPost historyPost) {

        String value = redisTemplate.opsForValue().get(
                RedisConstants.POST_HISTORY + historyPost.getUserId() + ":" + historyPost.getPostId());

        if (StrUtil.isEmpty(value)) {
            historyPost.setUpdateDate(new Date());
            int insert = historyPostMapper.insert(historyPost);
            redisTemplate.opsForValue().set(
                    RedisConstants.POST_HISTORY + historyPost.getUserId() + ":" + historyPost.getPostId(),
                    String.valueOf(historyPost.getHistoryId())
            );
            return Result.success(historyPost.getHistoryId());
        } else {
            historyPost.setHistoryId(Long.valueOf(value));
            return Result.success(historyPostMapper.updateById(historyPost));
        }
    }

    @Override
    public Result qryHistoryPage(UserHistoryPostPageReq req) {
        IPage<PostExt> result =  historyPostMapper.qryHistoryPage(req.toPage(), req);
        return Result.success(result, "查询成功!");
    }


}
