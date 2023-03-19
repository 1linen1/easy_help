package com.ateh.eh.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 类说明：所有实体类的父类
 * <p>
 * 类名称: BaseEntity.java
 *
 * @author huang.yijie
 * 时间: 2023/3/19 19:26
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
public class BaseEntity {

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private Date createDate;

    @TableField(value = "create_staff", fill = FieldFill.INSERT)
    @ApiModelProperty("创建人")
    private String createStaff;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private Date updateDate;

    @TableField(value = "update_staff", fill = FieldFill.UPDATE)
    @ApiModelProperty("更新人")
    private String updateStaff;
}
