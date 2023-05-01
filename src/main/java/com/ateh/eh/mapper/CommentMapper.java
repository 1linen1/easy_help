package com.ateh.eh.mapper;

import com.ateh.eh.entity.Comment;
import com.ateh.eh.entity.ext.CommentExt;
import com.ateh.eh.req.comment.CommentPageReq;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: CommentMapper.java
 *
 * @author huang.yijie
 * 时间: 2023/4/24 15:10
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    Page<CommentExt> qryPostCommentPage(@Param("page") Page<CommentExt> page, @Param("req") CommentPageReq req);
}
