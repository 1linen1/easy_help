package com.ateh.eh.req.message;

import com.ateh.eh.req.base.BasePageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: MessagePageReq.java
 *
 * @author huang.yijie
 * 时间: 2023/5/9 22:43
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("消息分页查询请求")
public class MessagePageReq extends BasePageReq {

    @ApiModelProperty("用户id")
    private Long sourceId;

    @ApiModelProperty("用户id")
    private Long targetId;

}
