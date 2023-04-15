package com.ateh.eh.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: UserRegisterReq.java
 *
 * @author huang.yijie
 * 时间: 2023/4/11 22:04
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@ApiModel("用户注册请求类")
@Data
public class UserRegisterReq {

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("邮箱验证码")
    private String code;

    @ApiModelProperty("密码")
    private String password;

}
