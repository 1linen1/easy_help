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
 * 类名称: Message.java
 *
 * @author huang.yijie
 * 时间: 2023/5/2 21:40
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("消息实体类")
public class Message extends BaseEntity {

    @ApiModelProperty("主键")
    @TableId(value = "message_id", type = IdType.AUTO)
    private Long messageId;

    @ApiModelProperty("发消息的用户id")
    private Long sourceId;

    @ApiModelProperty("接收消息的用户id")
    private Long targetId;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("是否已读（0未读，1已读）")
    private String isRead;

    @ApiModelProperty("消息类型（待定）")
    private String type;

}
