package com.ateh.eh.req.warning;

import com.ateh.eh.req.base.BasePageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: WarningPageReq.java
 *
 * @author huang.yijie
 * 时间: 2023/5/15 23:10
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("分页查询举报信息")
public class WarningPageReq extends BasePageReq {

    @ApiModelProperty("排序字段")
    private String sortedField;

    @ApiModelProperty("排序类型ASC/DESC")
    private String sortedType;

    @ApiModelProperty("处理结果")
    private String result;

    @ApiModelProperty("举报内容类型（0评论，1帖子，2用户，3帖子申诉）")
    private String type;

    @ApiModelProperty("举报者")
    private Long positiveUserId;

    @ApiModelProperty("被举报者")
    private Long passiveUserId;

    @ApiModelProperty("状态（00X失效，00A有效，00B举报成功，00C举报失败）")
    private String status;
}
