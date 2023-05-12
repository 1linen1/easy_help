package com.ateh.eh.mapper;

import com.ateh.eh.entity.Message;
import com.ateh.eh.req.message.MessagePageReq;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: MessageMapper.java
 *
 * @author huang.yijie
 * 时间: 2023/5/2 21:46
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 功能描述: 分页查询消息
     *
     * @return com.ateh.eh.utils.Result<com.baomidou.mybatisplus.core.metadata.IPage < com.ateh.eh.entity.Message>>
     * @author huang.yijie
     * 时间: 2023/5/9 23:08
     */
    IPage<Message> qryMessagePage(@Param("page") Page<IPage<Message>> toPage, @Param("req") MessagePageReq req);
}
