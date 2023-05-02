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
 * 类名称: Follows.java
 *
 * @author huang.yijie
 * 时间: 2023/5/1 21:51
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("关注列表实体类")
public class Follows extends BaseEntity {

    @ApiModelProperty("主键")
    @TableId(value = "follows_id", type = IdType.AUTO)
    private Long followsId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("粉丝id")
    private Long followId;

}
