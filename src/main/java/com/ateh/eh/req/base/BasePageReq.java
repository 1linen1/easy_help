package com.ateh.eh.req.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: BasePageReq.java
 *
 * @author huang.yijie
 * 时间: 2023/4/9 21:54
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@Data
@ApiModel("分页查询基本类")
public class BasePageReq implements Serializable {

    private static final long serialVersionUID = -6550827640887727324L;

    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize = 10;

    public <T> Page<T> toPage() {
        return new Page<>(getPageNum(), getPageSize());
    }

    public Integer getPageNum() {
        return pageNum != null ? pageNum : 1;
    }

    public Integer getPageSize() {
        return pageSize != null ? pageSize : 10;
    }

}
