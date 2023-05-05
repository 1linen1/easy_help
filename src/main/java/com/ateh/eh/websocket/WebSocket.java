package com.ateh.eh.websocket;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ateh.eh.common.CommonConstants;
import com.ateh.eh.entity.Message;
import com.ateh.eh.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")  // 接口路径 ws://localhost:8087/webSocket/userId;
public class WebSocket {

    private MessageMapper messageMapper;

    /**
     * spring管理的都是单例（singleton），和 websocket （多对象）相冲突。websocket在客户端每建立一个链接就会创建一个新的对象，这个对象没有任何的bean注入操作。
     * 项目启动时初始化，会初始化 websocket （非用户连接的），spring 同时会为其注入 service，该对象的 service 不是 null，被成功注入。
     * 但是，由于 spring 默认管理的是单例，所以只会注入一次 service。当新用户进入聊天时，系统又会创建一个新的 websocket 对象，
     * 这时矛盾出现了：spring 管理的都是单例，不会给第二个 websocket 对象注入 service，所以导致只要是用户连接创建的 websocket 对象，都不能再注入了。
     */

    /**
     * 用来获取messageMapper
     */
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocket.applicationContext = applicationContext;
    }

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 用户ID
     */
    private String userId;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    private static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId")String userId) {

        try {
            this.session = session;
            this.userId = userId;
            messageMapper = applicationContext.getBean(MessageMapper.class);
            if (!Objects.isNull(sessionPool.get(userId))) {
                log.info("已存在连接，总数为:" + webSockets.size());
                return;
            }
            webSockets.add(this);
            sessionPool.put(userId, session);
            log.info("【websocket消息】有新的连接，总数为:"+webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息】连接断开，总数为:"+webSockets.size());
        } catch (Exception e) {
        }
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端消息:" + message);
        JSONObject jsonObject = JSON.parseObject(message);
        String content = (String) jsonObject.get("content");
        String userId = (String) jsonObject.get("userId");
        if (StrUtil.isNotEmpty(userId) && StrUtil.isNotEmpty(content)) {
            sendOneMessage(userId, content);
        }
    }

    /** 发送错误时的处理
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误,原因:"+error.getMessage());
        error.printStackTrace();
    }


    // 此为广播消息
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:"+message);
        for(WebSocket webSocket : webSockets) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userId, String content) {
        Message msg = new Message();
        msg.setContent(content);
        msg.setSourceId(Long.valueOf(this.userId));
        msg.setTargetId(Long.valueOf(userId));
        msg.setIsRead(CommonConstants.IS_READ_FALSE);
        msg.setType(CommonConstants.IS_READ_FALSE);

        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:"+content);

                msg.setIsRead(CommonConstants.IS_READ_TRUE);
                messageMapper.insert(msg);
                session.getAsyncRemote().sendText(JSON.toJSONString(msg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            messageMapper.insert(msg);
        }
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for(String userId:userIds) {
            Session session = sessionPool.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:" + message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
