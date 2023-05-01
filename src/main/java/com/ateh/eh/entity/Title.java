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
 * 类名称: Title.java
 *
 * @author huang.yijie
 * 时间: 2023/4/17 13:52
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("帖子实体类")
public class Title extends BaseEntity {

    @ApiModelProperty("主键")
    @TableId(value = "title_id", type = IdType.AUTO)
    private Long titleId;

    @ApiModelProperty("名称")
    private String name;

}
