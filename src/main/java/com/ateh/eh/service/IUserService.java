package com.ateh.eh.service;

import com.ateh.eh.entity.User;
import com.ateh.eh.mapper.UserMapper;
import com.ateh.eh.req.user.UserLoginReq;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: IUserService.java
 *
 * @author huang.yijie
 * 时间: 2023/3/19 21:25
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
public interface IUserService extends IService<User>{

    /**
     * 用户登录
     *
     * @param req
     * @return
     */
    public Result login(UserLoginReq req);


    /**
     * 退出登录
     *
     * @return
     */
    public Result logout();

}
