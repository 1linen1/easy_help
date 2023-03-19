package com.ateh.eh.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: ResponseUtil.java
 *
 * @author huang.yijie
 * 时间: 2023/3/20 00:28
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
public class ResponseUtil {

    public static void out(HttpServletResponse response, String message, int code) {
        try {
            String json = JSON.toJSONString(Result.error(code, message));
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
