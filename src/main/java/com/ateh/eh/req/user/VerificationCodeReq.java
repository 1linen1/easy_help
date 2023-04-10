package com.ateh.eh.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: VerificationCodeReq.java
 *
 * @author huang.yijie
 * 时间: 2023/4/9 23:41
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@ApiModel("获取邮箱验证码请求类")
public class VerificationCodeReq {

    @ApiModelProperty("注册的邮箱")
    private String email;

}
