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
 * 类名称: RankPage.java
 *
 * @author huang.yijie
 * 时间: 2023/4/18 14:30
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("获取我的信息请求")
public class MyRankReq extends BasePageReq {

    @ApiModelProperty("排序类型，Current现积分，Total总积分")
    private String orderType;

    @ApiModelProperty("用户主键")
    private Long userId;

}
