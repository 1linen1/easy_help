package com.ateh.eh.service.impl;

import com.ateh.eh.entity.UserHelpPost;
import com.ateh.eh.entity.UserHistoryPost;
import com.ateh.eh.mapper.UserHelpPostMapper;
import com.ateh.eh.mapper.UserHistoryPostMapper;
import com.ateh.eh.service.IUserHelpPostService;
import com.ateh.eh.service.IUserHistoryPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: UserHistoryPostServiceImpl.java
 *
 * @author huang.yijie
 * 时间: 2023/5/7 14:53
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Service
public class UserHelpPostServiceImpl extends ServiceImpl<UserHelpPostMapper, UserHelpPost> implements IUserHelpPostService {
}
