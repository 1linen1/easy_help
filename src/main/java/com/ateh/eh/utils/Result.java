package com.ateh.eh.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    /**
     * 状态码：200为正确，100为出错
     */
    @ApiModelProperty("返回状态 200正常 100异常")
    private Integer code;

    @ApiModelProperty("返回数据")
    private T data;

    @ApiModelProperty("返回信息")
    private String msg;

    private Result(String msg) {
        code = 200;
        this.msg = msg;
    }
    private Result(T data) {
        code = 200;
        this.data = data;
    }

    public static <E> Result<E> success(E data, String msg) {
        return new Result<>(200, data, msg);
    }

    public static <E> Result<E> success(String msg) {
        return new Result<>(msg);
    }

    public static <E> Result<E> success(E data) {
        return new Result<>(data);
    }

    public static <S> Result<S> error(String msg) {
        return new Result<>(100, null, msg);
    }

    public static <S> Result<S> error(Integer code, String msg) {
        return new Result<>(code, null, msg);
    }
}