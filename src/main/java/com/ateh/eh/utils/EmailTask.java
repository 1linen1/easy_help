package com.ateh.eh.utils;

import cn.hutool.core.util.RandomUtil;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.Email;
import com.ateh.eh.mapper.EmailMapper;
import io.swagger.annotations.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Component
@Scope(name = "prototype", description = "邮件发送")
public class EmailTask implements Serializable {

    /**
     * 获得发件人信息
     */
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EmailMapper emailMapper;

    @Async
    public void sendAsync(String toEmail) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(fromEmail);
        smm.setTo(toEmail);
        smm.setSubject("《易帮》邮箱验证码");
        String verCode = RandomUtil.randomNumbers(6);
        smm.setText("尊敬的用户您好:"
                + "\n您的验证码是：" + verCode + "，本验证码 5 分钟内效，请及时输入（请勿泄露此验证码），如非本人操作，请忽略该邮件。");
        javaMailSender.send(smm);
        redisTemplate.opsForValue().set(RedisConstants.EMAIL_VERIFICATION_CODE + toEmail, verCode, RedisConstants.EMAIL_CODE_VALID_TIME,  TimeUnit.SECONDS);
        emailMapper.insert(new Email(toEmail, fromEmail, smm.getSubject(), smm.getText()));
    }
    
}
