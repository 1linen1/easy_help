package com.ateh.eh.entity;

import com.ateh.eh.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: Warning.java
 *
 * @author huang.yijie
 * 时间: 2023/5/1 20:50
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("举报信息实体类")
public class Warning extends BaseEntity {

    @ApiModelProperty("用户主键")
    @TableId(value = "warning_id", type = IdType.AUTO)
    private Long warningId;

    @ApiModelProperty("举报者id")
    private Long positiveUserId;

    @ApiModelProperty("被举报者id")
    private Long passiveUserId;

    @ApiModelProperty("被举报内容id")
    private Long commentPostId;

    @ApiModelProperty("被举报内容类型（0评论，1帖子，2用户）")
    private String type;

    @ApiModelProperty("举报结果")
    private String result;
}
