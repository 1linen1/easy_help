package com.ateh.eh.mapper;

import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.User;
import com.ateh.eh.entity.ext.UserExt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: UserMapper.java
 *
 * @author huang.yijie
 * 时间: 2023/3/19 20:29
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名称查询用户
     *
     * @param username 用户名称
     * @return
     */
    UserExt qryUserByName(@Param("username") String username);

    /**
     * 功能描述: 更新用户当前积分
     *
     * @return void
     * @author huang.yijie
     * 时间: 2023/5/8 0:09
     */
    void updateScoresCurrent(@Param("scores") Long scores, @Param("userId") Long userId);

    /**
     * 功能描述: 获取聊天用户
     *
     * @return java.util.List<com.ateh.eh.entity.User>
     * @author huang.yijie
     * 时间: 2023/5/10 21:57
     */
    List<UserExt> qryChatList(@Param("userId") Long userId);

    /**
     * 功能描述: 获取当前的用户帮助列表
     *
     * @return java.util.List<com.ateh.eh.entity.ext.UserExt>
     * @author huang.yijie
     * 时间: 2023/5/11 13:22
     */
    List<UserExt> qryHelpUserList(@Param("req") Post req);

    /**
     * 功能描述: 获取用户信息
     *
     * @return java.lang.Object
     * @author huang.yijie
     * 时间: 2023/5/12 15:32
     */
    UserExt getUserInfo(@Param("userId") Long userId);
}
