package com.ateh.eh;

import com.ateh.eh.entity.User;
import com.ateh.eh.mapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class EasyHelpApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("2696839754@qq.com");
        user.setRole("1");
        user.setStatus("00A");
        user.setLocked("0");
        System.out.println(userMapper.insert(user));

    }

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    void createPwd() {
        System.out.println(encoder.encode("admin"));
    }

}
