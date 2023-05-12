package com.ateh.eh.req.posts;

import com.ateh.eh.entity.ext.UserExt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: UpdateUserScoresReq.java
 *
 * @author huang.yijie
 * 时间: 2023/5/11 16:45
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@ApiModel("更新积分")
public class UpdateUserScoresReq {

    @ApiModelProperty("帮助用户列表")
    private List<UserExt> userExtList;

    @ApiModelProperty("帖子id")
    private Long postId;

    @ApiModelProperty("帖子用户的id")
    private Long userId;

}
