package com.ateh.eh.req.warning;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: HandleWarningReq.java
 *
 * @author huang.yijie
 * 时间: 2023/5/16 16:17
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@ApiModel("举报处理")
public class HandleWarningReq {

    @ApiModelProperty("举报id")
    private Long warningId;

    @ApiModelProperty("帖子id")
    private Long postId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("是否删除帖子")
    private Boolean isDeletePost;

    @ApiModelProperty("是否删除用户")
    private Boolean isDeleteUser;

    @ApiModelProperty("是否删除评论")
    private Boolean isDeleteComment;

}
