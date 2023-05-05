package com.ateh.eh.controller;

import com.ateh.eh.entity.Post;
import com.ateh.eh.entity.ext.PostExt;
import com.ateh.eh.req.posts.PostPageReq;
import com.ateh.eh.service.IPostService;
import com.ateh.eh.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类说明：TODO
 * <p>
 * 类名称: FileController.java
 *
 * @author huang.yijie
 * 时间: 2023/3/30 22:30
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
@RestController
@RequestMapping("/api/post")
@Api("帖子接口")
public class PostController {

    @Autowired
    private IPostService postService;

    @PostMapping("/addPost")
    @ApiOperation("新增帖子")
    public Result addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }

    @PostMapping("/qryPostPage")
    @ApiOperation("分页查询帖子")
    public Result<IPage<PostExt>> qryPostPage(@RequestBody PostPageReq req) {
        return postService.qryPostPage(req);
    }

    @GetMapping("/addViews/{postId}")
    @ApiOperation("增加浏览量")
    public Result addPostViews(@PathVariable("postId") Long postId) {
        return postService.addPostViews(postId);
    }

}
