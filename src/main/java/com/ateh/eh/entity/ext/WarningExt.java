package com.ateh.eh.entity.ext;

import com.ateh.eh.entity.Warning;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: WarningExt.java
 *
 * @author huang.yijie
 * 时间: 2023/5/15 23:23
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("举报信息拓展")
public class WarningExt extends Warning {

    @ApiModelProperty("举报者头像")
    private String positiveAvatar;

    @ApiModelProperty("举报者名称")
    private String positiveNickname;

    @ApiModelProperty("被举报者头像")
    private String passiveAvatar;

    @ApiModelProperty("被举报者名称")
    private String passiveNickname;

    @ApiModelProperty("被举报者状态")
    private String passiveUserStatus;

}
