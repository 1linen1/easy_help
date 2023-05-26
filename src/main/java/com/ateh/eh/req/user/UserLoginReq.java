package com.ateh.eh.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 类说明：用户登录请求
 * <p>
 * 类名称: UserLoginReq.java
 *
 * @author huang.yijie
 * 时间: 2023/3/19 21:23
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@ApiModel("用户登录请求")
public class UserLoginReq {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("角色（1管理员，2普通用户）")
    private String role;

}
