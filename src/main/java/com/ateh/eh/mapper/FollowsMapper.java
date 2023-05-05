package com.ateh.eh.mapper;

import com.ateh.eh.entity.Follows;
import com.ateh.eh.entity.ext.UserExt;
import com.ateh.eh.req.follows.FollowsPageReq;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: FollowMapper.java
 *
 * @author huang.yijie
 * 时间: 2023/5/1 23:10
 */
@Mapper
public interface FollowsMapper extends BaseMapper<Follows> {

    /**
     * 查询粉丝/关注
     *
     * @param req
     * @return
     */
    IPage<UserExt> qryConcernsOrFollowsPage(@Param("page") IPage<UserExt> page, @Param("req") FollowsPageReq req);
}
