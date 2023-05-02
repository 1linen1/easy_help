package com.ateh.eh.service.impl;

import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.Follows;
import com.ateh.eh.mapper.FollowsMapper;
import com.ateh.eh.service.IFollowsService;
import com.ateh.eh.utils.Result;
import com.ateh.eh.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: FollowsServiceImpl.java
 *
 * @author huang.yijie
 * 时间: 2023/5/1 23:11
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Service
public class FollowsServiceImpl extends ServiceImpl<FollowsMapper, Follows> implements IFollowsService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private FollowsMapper followsMapper;

    @Override
    public Result addFollow(Follows follows) {
        String key = RedisConstants.FOLLOWS + follows.getUserId();

        int i = followsMapper.insert(follows);

        if (i >= 1) {
            redisTemplate.opsForSet().add(key, follows.getFollowId().toString());
            return Result.success("关注成功");
        }

        return Result.error("关注失败!");
    }

    @Override
    public Result removeFollow(Follows follows) {
        String key = RedisConstants.FOLLOWS + follows.getUserId();

        follows.setStatus(CommonConstants.STATUS_INVALID);

        LambdaQueryWrapper<Follows> lqw = new LambdaQueryWrapper<>();

        lqw.eq(Follows::getUserId, follows.getUserId());
        lqw.eq(Follows::getFollowId, follows.getFollowId());
        lqw.eq(Follows::getStatus, CommonConstants.STATUS_VALID);

        int i = followsMapper.update(follows, lqw);

        if (i >= 1) {
            redisTemplate.opsForSet().remove(key, follows.getFollowId().toString());
            return Result.success("取关成功!");
        }

        return Result.error("取关失败!");
    }

    @Override
    public Result isFollow(Long userId) {
        String key = RedisConstants.FOLLOWS + userId;
        Long id = UserHolder.getLoginUser().getUserId();
        Boolean isMember = redisTemplate.opsForSet().isMember(key, id.toString());

        return Result.success(isMember, "查询成功");
    }
}
