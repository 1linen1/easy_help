package com.ateh.eh.entity.ext;

import com.ateh.eh.entity.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: PostExt.java
 *
 * @author huang.yijie
 * 时间: 2023/4/8 20:39
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostExt extends Post {

    @ApiModelProperty("用户主键")
    private Long userId;

    @ApiModelProperty("用户别名")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

}
