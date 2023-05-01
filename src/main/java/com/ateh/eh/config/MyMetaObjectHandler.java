package com.ateh.eh.config;

import cn.hutool.core.util.StrUtil;
import com.ateh.eh.auth.LoginUser;
import com.ateh.eh.common.CommonConstants;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 类说明：元对象处理器（MP的自动插入配置）
 * <p>
 * 类名称: MyMetaObjectHandler.java
 *
 * @author huang.yijie
 * 时间: 2023/3/19 20:08
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        // 或者
        this.strictInsertFill(metaObject, "createDate", Date::new, Date.class);
        this.strictInsertFill(metaObject, "status", () -> CommonConstants.STATUS_VALID, String.class);
        this.strictInsertFill(metaObject, "createStaff", this::getUsername, String.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateDate", Date::new, Date.class);
        this.strictUpdateFill(metaObject, "updateStaff", this::getUsername, String.class);
    }

    /**
     * 获取用户姓名
     *
     * @return 用户姓名
     */
    private String getUsername() {
        // 从上下文中获取用户信息
        SecurityContext context = SecurityContextHolder.getContext();

        if (context.getAuthentication().getPrincipal() instanceof String) {
            return "";
        }
        LoginUser loginUser = (LoginUser) context.getAuthentication().getPrincipal();
        return loginUser.getUsername();
    }
}