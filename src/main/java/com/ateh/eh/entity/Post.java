package com.ateh.eh.entity;

import com.ateh.eh.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: Posts.java
 *
 * @author huang.yijie
 * 时间: 2023/4/8 19:30
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("帖子实体类")
public class Post extends BaseEntity {

    @ApiModelProperty("帖子主键")
    @TableId(value = "post_id", type = IdType.AUTO)
    private Long postId;

    @ApiModelProperty("发布帖子的用户主键")
    private Long userId;

    @ApiModelProperty("帖子的内容")
    private String content;

    @ApiModelProperty("图片路径")
    private String images;

    @ApiModelProperty("帖子类型（0求助贴，1动态贴）")
    private String type;

    @ApiModelProperty("帖子标签")
    private String tag;

    @ApiModelProperty("点赞数")
    private Long loves;

    @ApiModelProperty("收藏数")
    private Long collects;

    @ApiModelProperty("浏览量")
    private Long views;

    @ApiModelProperty("评论数")
    private Long comments;

    @ApiModelProperty("用户附加的积分")
    private Long scores;
}
