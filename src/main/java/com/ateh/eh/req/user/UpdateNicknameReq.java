package com.ateh.eh.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: UpdateNicknameReq.java
 *
 * @author huang.yijie
 * 时间: 2023/4/16 22:05
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@ApiModel("更新昵称请求")
public class UpdateNicknameReq {

    @ApiModelProperty("用户主键")
    private Long userId;

    @ApiModelProperty("新昵称")
    private String nickname;

}
