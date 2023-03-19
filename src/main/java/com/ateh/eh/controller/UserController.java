package com.ateh.eh.controller;

import com.ateh.eh.req.user.UserLoginReq;
import com.ateh.eh.service.IUserService;
import com.ateh.eh.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: UserController.java
 *
 * @author huang.yijie
 * 时间: 2023/3/19 21:17
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@RestController
@RequestMapping("/api/user")
@Api("用户接口")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result login(@RequestBody UserLoginReq req) {
        return userService.login(req);
    }

    @GetMapping("/logout")
    @ApiOperation("用户退出")
    public Result logout() {
        return userService.logout();
    }

}
