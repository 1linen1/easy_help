package com.ateh.eh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: ToEmail.java
 *
 * @author huang.yijie
 * 时间: 2023/4/9 23:44
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("邮件实体类")
public class Email {

    @ApiModelProperty("主键")
    @TableId(value = "email_id", type = IdType.AUTO)
    private Long emailId;

    @ApiModelProperty("邮件接收方")
    private String toEmail;

    @ApiModelProperty("邮件发送方")
    private String fromEmail;

    @ApiModelProperty("邮件主题")
    private String subject;

    @ApiModelProperty("邮件内容")
    private String content;

    public Email(String toEmail, String fromEmail, String subject, String content) {
        this.toEmail = toEmail;
        this.fromEmail = fromEmail;
        this.subject = subject;
        this.content = content;
    }
}
