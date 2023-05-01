package com.ateh.eh.req.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: AddCommentReq.java
 *
 * @author huang.yijie
 * 时间: 2023/4/24 15:25
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@ApiModel("新增评论请求")
@Data
public class AddCommentReq {

    @ApiModelProperty("帖子id")
    private Long postId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("父评论id")
    private Long parentId;

    @ApiModelProperty("评论内容")
    private String content;

}
