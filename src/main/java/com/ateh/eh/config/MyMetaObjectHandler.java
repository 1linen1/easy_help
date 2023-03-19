package com.ateh.eh.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        this.strictInsertFill(metaObject, "createDate",
                () -> DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()), String.class);
        this.strictInsertFill(metaObject, "createStaff", () -> "admin", String.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime",
                () -> DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()), String.class);
        this.strictUpdateFill(metaObject, "updateStaff", () -> "admin", String.class);
    }
}