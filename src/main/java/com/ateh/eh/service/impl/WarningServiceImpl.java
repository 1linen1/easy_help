package com.ateh.eh.service.impl;

import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.entity.Warning;
import com.ateh.eh.mapper.WarningMapper;
import com.ateh.eh.service.IWarningService;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: WarningServiceImpl.java
 *
 * @author huang.yijie
 * 时间: 2023/5/1 21:11
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Service
public class WarningServiceImpl extends ServiceImpl<WarningMapper, Warning> implements IWarningService {
    @Autowired
    private WarningMapper warningMapper;

    @Override
    public Result addWarning(Warning warning) {
        LambdaQueryWrapper<Warning> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Warning::getCommentPostId, warning.getCommentPostId());
        lqw.eq(Warning::getPositiveUserId, warning.getPositiveUserId());
        lqw.eq(Warning::getPassiveUserId, warning.getPassiveUserId());
        lqw.eq(Warning::getType, warning.getType());
        Long count = warningMapper.selectCount(lqw);
        if (count > 0) {
            return Result.error("您已举报过该内容!");
        }
        warningMapper.insert(warning);
        return Result.success("举报成功!");
    }

    @Override
    public Result handleWarning(Warning warning) {
        warningMapper.updateById(warning);
        return Result.success("处理成功!");
    }
}
