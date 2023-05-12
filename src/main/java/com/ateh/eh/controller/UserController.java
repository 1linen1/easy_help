package com.ateh.eh.controller;

import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.ext.UserExt;
import com.ateh.eh.req.posts.UpdateUserScoresReq;
import com.ateh.eh.req.user.MyRankReq;
import com.ateh.eh.req.user.RankPageReq;
import com.ateh.eh.req.user.UpdateNicknameReq;
import com.ateh.eh.req.user.UserRegisterReq;
import com.ateh.eh.req.user.VerificationCodeReq;
import com.ateh.eh.req.user.UserLoginReq;
import com.ateh.eh.service.IUserService;
import com.ateh.eh.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/getVerificationCode")
    @ApiOperation("获取邮箱验证码")
    public Result getVerificationCode(@RequestBody VerificationCodeReq loginCodeReq) {
        return userService.getVerificationCode(loginCodeReq);
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public Result register(@RequestBody UserRegisterReq req) {
        return userService.register(req);
    }

    @PostMapping("/updateNickname")
    @ApiOperation("更新昵称")
    public Result updateNickname(@RequestBody UpdateNicknameReq req) {
        return userService.updateNickname(req);
    }

    @PostMapping("/getRankPage")
    @ApiOperation("获取排行榜")
    public Result getRankPage(@RequestBody RankPageReq req) {
        return userService.getRankPage(req);
    }

    @PostMapping("/getMyRank")
    @ApiOperation("获取当前用户排名")
    public Result getMyRank(@RequestBody MyRankReq req) {
        return userService.getMyRank(req);
    }

    @GetMapping("/getUserInfo/{userId}")
    @ApiOperation("获取当前用户信息")
    public Result getUserInfo(@PathVariable("userId")Long userId) {
        return userService.getUserInfo(userId);
    }

    @GetMapping("/qryChatList/{userId}")
    @ApiOperation("获取聊天用户")
    public Result qryChatList(@PathVariable("userId") Long userId) {
        return userService.qryChatList(userId);
    }

    @PostMapping("/qryHelpUserList")
    @ApiOperation("获取当前帮助的用户")
    public Result qryHelpUserList(@RequestBody Post req) {
        return userService.qryHelpUserList(req);
    }

    @PostMapping("/updateUserScores")
    @ApiOperation("更新用户积分")
    public Result updateUserScores(@RequestBody UpdateUserScoresReq req) {
        return userService.updateUserScores(req);
    }
}
