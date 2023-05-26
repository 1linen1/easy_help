package com.ateh.eh.mapper;

import com.ateh.eh.entity.Warning;
import com.ateh.eh.entity.ext.WarningExt;
import com.ateh.eh.req.warning.WarningPageReq;
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
 * 类名称: WarningMapper.java
 *
 * @author huang.yijie
 * 时间: 2023/5/1 21:07
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Mapper
public interface WarningMapper extends BaseMapper<Warning> {

    /**
     * 功能描述: 查询所有举报信息
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/15 23:28
     */
    IPage<WarningExt> qryAllWarning(@Param("page") Page<WarningExt> toPage, @Param("req") WarningPageReq req);

    /**
     * 功能描述: 查询我的举报信息
     *
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.ateh.eh.entity.ext.WarningExt>
     * @author huang.yijie
     * 时间: 2023/5/17 16:08
     */
    IPage<WarningExt> qryMyWarning(@Param("page") Page<WarningExt> toPage, @Param("req") WarningPageReq req);
}
