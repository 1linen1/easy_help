package com.ateh.eh.req.user;

import com.ateh.eh.req.base.BasePageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: UserPageReq.java
 *
 * @author huang.yijie
 * 时间: 2023/5/14 23:48
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户请求")
public class UserPageReq extends BasePageReq {

    @ApiModelProperty("用户名")
    private String nickname;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("总积分")
    private String scoresTotal;

    @ApiModelProperty("现积分")
    private String scoresCurrent;
}
