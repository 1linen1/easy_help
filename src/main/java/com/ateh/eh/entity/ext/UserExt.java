package com.ateh.eh.entity.ext;

import com.ateh.eh.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: UserExt.java
 *
 * @author huang.yijie
 * 时间: 2023/4/17 13:56
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户拓展类")
public class UserExt extends User {

    @ApiModelProperty("称号")
    private String titleName;

    @ApiModelProperty("排名")
    private Long rank;

}
