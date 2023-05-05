package com.ateh.eh.service;

import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.req.posts.PostPageReq;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: ${NAME}.java
 *
 * @author huang.yijie
 * 时间: ${DATE} ${HOUR}:${MINUTE}
 */
public interface IPostService extends IService<Post> {

    /**
     * 功能描述: 添加帖子
     *
     * @param post 待添加的帖子信息
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/4/8 20:03
     */
    Result addPost(Post post);

    /**
     * 功能描述: 分页查询帖子
     *
     * @return com.ateh.eh.utils.Result<com.baomidou.mybatisplus.core.metadata.IPage < com.ateh.eh.entity.ext.PostExt>>
     * @author huang.yijie
     * 时间: 2023/4/23 0:26
     */
    Result<IPage<PostExt>> qryPostPage(PostPageReq req);

    /**
     * 功能描述: 新增帖子浏览量
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/5 22:40
     */
    Result addPostViews(Long postId);
}
