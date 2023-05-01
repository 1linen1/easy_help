package com.ateh.eh.req.comment;

import com.ateh.eh.req.base.BasePageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: CommentPageReq.java
 *
 * @author huang.yijie
 * 时间: 2023/4/24 15:19
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("分页查询帖子评论请求")
public class CommentPageReq extends BasePageReq {

    @ApiModelProperty("帖子ID")
    private Long postId;

}
