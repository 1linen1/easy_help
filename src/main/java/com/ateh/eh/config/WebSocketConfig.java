package com.ateh.eh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: WebSocketConfig.java
 *
 * @author huang.yijie
 * 时间: 2023/5/2 21:01
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
