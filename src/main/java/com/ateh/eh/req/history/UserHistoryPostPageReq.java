package com.ateh.eh.req.history;

import com.ateh.eh.req.base.BasePageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: UserHistoryPostPageReq.java
 *
 * @author huang.yijie
 * 时间: 2023/5/7 15:47
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("浏览历史查询请求")
public class UserHistoryPostPageReq extends BasePageReq {

    @ApiModelProperty("用户id")
    private Long userId;

}
