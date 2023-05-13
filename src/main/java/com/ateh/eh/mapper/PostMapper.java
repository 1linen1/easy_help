package com.ateh.eh.mapper;

import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.req.posts.PostPageReq;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: ${NAME}.java
 *
 * @author huang.yijie
 * 时间: ${DATE} ${HOUR}:${MINUTE}
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 分页查询帖子
     *
     * @param page 分页信息
     * @param req 请求信息
     * @return
     */
    public IPage<PostExt> qryPostPage(@Param("page")IPage<Post> page, @Param("req")PostPageReq req);

    /**
     * 功能描述: 分页查询帮助过的帖子
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/11 21:04
     */
    IPage<PostExt> qryHelpPostPage(@Param("page")Page<PostExt> toPage, @Param("req")PostPageReq req);

    /**
     * 功能描述: 根据帖子内容/用户名称分页查询帖子
     *
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.ateh.eh.entity.ext.PostExt>
     * @author huang.yijie
     * 时间: 2023/5/12 15:04
     */
    IPage<PostExt> qryPostPageByContent(@Param("page") Page<PostExt> toPage, @Param("req") PostPageReq req);

    /**
     * 功能描述: 查询用户动态
     *
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.ateh.eh.entity.ext.PostExt>
     * @author huang.yijie
     * 时间: 2023/5/12 19:25
     */
    IPage<PostExt> qryDynamicPage(@Param("page") Page<PostExt> toPage, @Param("req") PostPageReq req, @Param("ids") List<Long> ids);

    /**
     * 功能描述: 随机取出数据
     *
     * @return java.util.List<com.ateh.eh.entity.ext.PostExt>
     * @author huang.yijie
     * 时间: 2023/5/13 16:07
     */
    List<PostExt> randomSelectPost(@Param("num") int num);

    /**
     * 功能描述: 根据推荐id查询帖子
     *
     * @return java.util.List<com.ateh.eh.entity.ext.PostExt>
     * @author huang.yijie
     * 时间: 2023/5/13 16:07
     */
    List<PostExt> selectByRecommendId(@Param("ids") List<Long> ids);
}
