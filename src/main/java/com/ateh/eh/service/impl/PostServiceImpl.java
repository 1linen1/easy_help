package com.ateh.eh.service.impl;

import com.ateh.eh.common.CommonConstants;
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
import org.springframework.stereotype.Service;

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

    @Override
    public Result addPost(Post post) {
        post.setUserId(UserHolder.getLoginUser().getUserId());
        postMapper.insert(post);
        return Result.success("发帖成功!");
    }

    @Override
    public Result<IPage<PostExt>> qryPostPage(PostPageReq req) {
        IPage<PostExt> posts = postMapper.qryPostPage(req.toPage(), req);
        return Result.success(posts);
    }

}
