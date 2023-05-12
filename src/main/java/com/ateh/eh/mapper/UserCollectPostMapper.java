package com.ateh.eh.mapper;

import com.ateh.eh.entity.UserCollectPost;
import com.ateh.eh.entity.UserHistoryPost;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.req.history.UserHistoryPostPageReq;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: UserHistoryPostMapper.java
 *
 * @author huang.yijie
 * 时间: 2023/5/7 14:51
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Mapper
public interface UserCollectPostMapper extends BaseMapper<UserCollectPost> {

    /**
     * 功能描述: 分页查询收藏的帖子
     *
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.ateh.eh.entity.UserCollectPost>
     * @author huang.yijie
     * 时间: 2023/5/9 14:11
     */
    IPage<PostExt> qryCollectPostPage(@Param("page") Page<PostExt> toPage, @Param("req") UserHistoryPostPageReq req);
}
