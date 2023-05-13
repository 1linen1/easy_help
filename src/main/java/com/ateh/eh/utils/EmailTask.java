package com.ateh.eh.utils;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.ateh.eh.common.RedisConstants;
import com.ateh.eh.entity.Email;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.mapper.EmailMapper;
import com.ateh.eh.mapper.PostMapper;
import com.mysql.cj.jdbc.MysqlDataSource;
import io.swagger.annotations.Scope;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Scope(name = "prototype", description = "邮件发送")
public class EmailTask implements Serializable {

    /**
     * 获得发件人信息
     */
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.datasource.druid.username}")
    private String username;

    @Value("${spring.datasource.druid.password}")
    private String password;

    @Value("${spring.datasource.druid.url}")
    private String url;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private EmailMapper emailMapper;

    @Async
    public void sendAsync(String toEmail) {
        try {
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
        } catch (MailException e) {
            throw new RuntimeException("邮箱不存在!");
        }
    }

    @Async
    public void calRecommendSync(Long userId) {
        try {
            // jdbc:mysql://127.0.0.1:3306/easy_help
            int doubleIndex = url.lastIndexOf("//");
            int semiIndex = url.lastIndexOf(":");
            int singleIndex = url.lastIndexOf("/");
            String serverName = url.substring(doubleIndex + 2, semiIndex);
            String port = url.substring(semiIndex + 1, singleIndex);

            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName(serverName);
            dataSource.setPort(Integer.parseInt(port));
            dataSource.setUser("root");
            dataSource.setPassword("123456");
            dataSource.setDatabaseName("easy_help");

            MySQLJDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource, "recommend", "user_id", "post_id", "loves", "create_date");

            // 计算相似度
            ItemSimilarity itemSimilarity = new UncenteredCosineSimilarity(dataModel);

            // 构建推荐器，使用基于物品的协同过滤推荐
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);

            List<RecommendedItem> recommendList = recommender.recommend(6, 100);
            List<PostExt> posts;
            if (recommendList.size() > 0) {
                List<Long> ids = recommendList.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
                posts = postMapper.selectByRecommendId(ids);
                String str = JSON.toJSONString(posts);
                redisTemplate.delete(RedisConstants.RECOMMEND_POST + userId);
                redisTemplate.opsForValue().set(RedisConstants.RECOMMEND_POST + userId, str);
            }
        } catch (TasteException e) {
            e.printStackTrace();
        }
    }
    
}
