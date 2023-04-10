package com.ateh.eh.mapper;

import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.req.posts.PostPageReq;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

}
