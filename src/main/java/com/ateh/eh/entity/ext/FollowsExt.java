package com.ateh.eh.entity.ext;

import com.ateh.eh.entity.Follows;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: FollowsExt.java
 *
 * @author huang.yijie
 * 时间: 2023/5/5 16:02
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("粉丝拓展类")
public class FollowsExt extends Follows {
}
