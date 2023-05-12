package com.ateh.eh;

import com.ateh.eh.websocket.WebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableSwagger2
@EnableAsync
@SpringBootApplication
public class EasyHelpApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EasyHelpApplication.class, args);

        WebSocket.setApplicationContext(run);
    }

}
