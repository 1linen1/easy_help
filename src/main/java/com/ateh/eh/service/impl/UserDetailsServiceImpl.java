package com.ateh.eh.service.impl;

import com.ateh.eh.auth.LoginUser;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.entity.Follows;
import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.ext.UserExt;
import com.ateh.eh.mapper.FollowsMapper;
import com.ateh.eh.mapper.PostMapper;
import com.ateh.eh.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private FollowsMapper followsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询数据库中的用户信息
        UserExt user = userMapper.qryUserByName(username);

        if (Objects.isNull(user)) {
            throw new RuntimeException("该用户不存在！");
        }

        LambdaQueryWrapper<Post> postLqw = new LambdaQueryWrapper<>();
        postLqw.eq(Post::getUserId, user.getUserId());
        postLqw.eq(Post::getStatus, CommonConstants.STATUS_VALID);
        postLqw.eq(Post::getType, CommonConstants.POST_TYPE_DYNAMIC);
        user.setDynamics(postMapper.selectCount(postLqw));

        LambdaQueryWrapper<Follows> followsLqw = new LambdaQueryWrapper<>();
        followsLqw.eq(Follows::getUserId, user.getUserId());
        followsLqw.eq(Follows::getStatus, CommonConstants.STATUS_VALID);
        user.setFollows(followsMapper.selectCount(followsLqw));

        followsLqw = new LambdaQueryWrapper<>();
        followsLqw.eq(Follows::getFollowId, user.getUserId());
        followsLqw.eq(Follows::getStatus, CommonConstants.STATUS_VALID);
        user.setConcerns(followsMapper.selectCount(followsLqw));

        // TODO 查询权限信息

        return new LoginUser(user, null);
    }
}