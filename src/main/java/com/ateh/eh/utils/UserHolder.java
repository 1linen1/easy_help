package com.ateh.eh.utils;

import com.ateh.eh.auth.LoginUser;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: UserHolder.java
 *
 * @author huang.yijie
 * 时间: 2023/4/8 22:47
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
public class UserHolder {

    public static LoginUser getLoginUser() {
        // 从上下文中获取用户信息
        SecurityContext context = SecurityContextHolder.getContext();
        return (LoginUser) context.getAuthentication().getPrincipal();
    }

}
