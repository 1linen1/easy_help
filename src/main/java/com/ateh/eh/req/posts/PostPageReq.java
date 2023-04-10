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

}
