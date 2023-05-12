package com.ateh.eh.service;

import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.User;
import com.ateh.eh.entity.ext.UserExt;
import com.ateh.eh.mapper.UserMapper;
import com.ateh.eh.req.posts.UpdateUserScoresReq;
import com.ateh.eh.req.user.MyRankReq;
import com.ateh.eh.req.user.RankPageReq;
import com.ateh.eh.req.user.UpdateNicknameReq;
import com.ateh.eh.req.user.UserLoginReq;
import com.ateh.eh.req.user.UserRegisterReq;
import com.ateh.eh.req.user.VerificationCodeReq;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

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

    /**
     * 功能描述: 获取邮箱验证码
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/4/9 23:56
     */
    Result getVerificationCode(VerificationCodeReq loginCodeReq);

    /**
     * 功能描述: 用户注册
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/4/11 22:08
     */
    Result register(UserRegisterReq req);

    /**
     * 功能描述: 修改用户昵称
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/4/16 22:07
     */
    Result updateNickname(UpdateNicknameReq req);

    /**
     * 功能描述: 分页获取排行榜数据
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/4/18 14:17
     */
    Result getRankPage(RankPageReq req);

    /**
     * 功能描述: 获取当前用户排名
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/4/18 16:18
     */
    Result getMyRank(MyRankReq req);

    /**
     * 功能描述: 获取当前用户信息
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/7 23:21
     */
    Result getUserInfo(Long userId);

    /**
     * 功能描述: 获取聊天用户
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/10 21:56
     */
    Result qryChatList(Long userId);

    /**
     * 功能描述: 获取当前帮助的用户
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/11 13:21
     */
    Result qryHelpUserList(Post req);

    /**
     * 功能描述: 更新用户积分
     *
     * @return com.ateh.eh.utils.Result
     * @author huang.yijie
     * 时间: 2023/5/11 15:34
     */
    Result updateUserScores(UpdateUserScoresReq req);
}
