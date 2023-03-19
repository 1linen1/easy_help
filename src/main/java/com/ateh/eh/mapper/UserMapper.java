package com.ateh.eh.mapper;

import com.ateh.eh.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
