package com.ateh.eh.entity;

import com.ateh.eh.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 类说明：用户实体类
 * <p>
 * 类名称: User.java
 *
 * @author huang.yijie
 * 时间: 2023/3/19 19:25
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户实体类")
public class User extends BaseEntity {

    @ApiModelProperty("用户主键")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户别名")
    private String nickname;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("角色")
    private String role;

    @ApiModelProperty("总积分")
    private Long scoresTotal;

    @ApiModelProperty("当前积分")
    private Long scoresCurrent;

    @ApiModelProperty("称号")
    private Long title;

    @ApiModelProperty("粉丝数")
    private String follows;

    @ApiModelProperty("关注数")
    private String concerns;

    @ApiModelProperty("动态数")
    private String dynamics;

    @ApiModelProperty("是否被锁")
    private String locked;

    @ApiModelProperty("最近一次登录时间")
    private Date lastLoginDate;

}
