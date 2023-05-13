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
 * 类名称: Recommend.java
 *
 * @author huang.yijie
 * 时间: 2023/5/13 13:27
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("协同推荐")
public class Recommend extends BaseEntity {

    @ApiModelProperty("主键")
    @TableId(value = "recommend_id", type = IdType.AUTO)
    private Long recommendId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("帖子id")
    private Long postId;

    @ApiModelProperty("用户喜好")
    private float loves;

}
