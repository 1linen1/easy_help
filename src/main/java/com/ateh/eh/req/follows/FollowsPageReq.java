package com.ateh.eh.req.follows;

import com.ateh.eh.req.base.BasePageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: FollowsPageReq.java
 *
 * @author huang.yijie
 * 时间: 2023/5/5 16:04
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("粉丝分页查询请求")
public class FollowsPageReq extends BasePageReq {

    @ApiModelProperty("查询类型：'follows'查粉丝，'concerns'查关注")
    private String type;

    @ApiModelProperty("用户id")
    private Long userId;

}
