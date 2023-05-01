package com.ateh.eh.mapper;

import com.ateh.eh.entity.User;
import com.ateh.eh.entity.ext.UserExt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

}
