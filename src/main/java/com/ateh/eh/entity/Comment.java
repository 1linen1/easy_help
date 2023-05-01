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
 * 类名称: Comment.java
 *
 * @author huang.yijie
 * 时间: 2023/4/24 12:03
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("评论实体类")
public class Comment extends BaseEntity {

    @ApiModelProperty("评论主键")
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    @ApiModelProperty("帖子ID")
    private Long postId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("父评论ID")
    private Long parentId;

    @ApiModelProperty("评论内容")
    private String content;

}
