package com.ateh.eh.req.warning;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: HandleAppealReq.java
 *
 * @author huang.yijie
 * 时间: 2023/5/16 21:45
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@ApiModel("申诉处理")
public class HandleAppealReq {

    @ApiModelProperty("举报id")
    private Long warningId;

    @ApiModelProperty("帖子id")
    private Long postId;

    @ApiModelProperty("是否分配了积分")
    private Boolean isNeedAssign;

    @ApiModelProperty("积分")
    private long scores;

    @ApiModelProperty("举报者用户")
    private Long positiveUserId;

}
