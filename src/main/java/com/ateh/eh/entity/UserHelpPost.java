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
 * 类名称: UserHistoryPost.java
 *
 * @author huang.yijie
 * 时间: 2023/5/7 14:47
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户帮助帖子记录")
public class UserHelpPost extends BaseEntity {

    @ApiModelProperty("主键")
    @TableId(value = "help_id", type = IdType.AUTO)
    private Long helpId;

    @ApiModelProperty("帮助者id")
    private Long positiveUserId;

    @ApiModelProperty("被帮助者id")
    private Long passiveUserId;

    @ApiModelProperty("帖子id")
    private Long postId;

    @ApiModelProperty("获得的积分")
    private Long scores;

}
