package com.ateh.eh.req.posts;

import com.ateh.eh.entity.Post;
import com.ateh.eh.req.base.BasePageReq;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：帖子分页请求
 * <p>
 * 类名称: PostsReq.java
 *
 * @author huang.yijie
 * 时间: 2023/3/30 22:43
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("帖子分页查询请求")
public class PostPageReq extends BasePageReq {

    @ApiModelProperty("排序类型, time时间，views浏览量，scores积分")
    private String sortedType;

    @ApiModelProperty("用户id，查询我的帖子使用")
    private Long userId;

    @ApiModelProperty("帖子内容/用户名称")
    private String content;

    @ApiModelProperty("帖子id")
    private Long postId;

    @ApiModelProperty("帖子类型")
    private String type;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("收藏")
    private String collects;

    @ApiModelProperty("浏览")
    private String views;

    @ApiModelProperty("用户附加积分")
    private String scores;

    @ApiModelProperty("是否已解决")
    private String resolved;

    @ApiModelProperty("用户名称")
    private String nickname;

}
